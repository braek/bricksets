package io.bricksets.rdbms;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockException;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.rdbms.tables.Event;
import io.bricksets.rdbms.tables.Tag;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

@Transactional
public class RdbmsBricksetRepository implements BricksetRepository, BricksetNumberService {

    private final DSLContext context;

    public RdbmsBricksetRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public boolean exists(BricksetNumber number) {
        return false;
    }

    @Override
    public Optional<Brickset> get(BricksetId bricksetId) {
        return Optional.empty();
    }

    @Override
    public void save(Brickset brickset) {
        var events = brickset.getMutatingEvents();

        // No mutations
        if (events.isEmpty()) {
            return;
        }

        // Optimistic locking
        var eventStream = query(brickset.getId());
        if (!Objects.equals(eventStream.getLastEventId(), brickset.getLastEventId())) {
            throw new EventStreamOptimisticLockException(brickset.getLastEventId());
        }

        events.forEach(event -> {

            // Store event
            var eventRecord = context.newRecord(Event.EVENT);
            eventRecord.setId(event.id().getValue());
            eventRecord.setOccurredOn(event.occurredOn().toLocalDateTime());
            eventRecord.setEventClass(event.getClass().getSimpleName());
            eventRecord.setEventValue(EventMapper.INSTANCE.serialize(event));
            eventRecord.store();

            // Store tags
            event.tags().forEach(tag -> {
                var tagRecord = context.newRecord(Tag.TAG);
                tagRecord.setEventId(event.id().getValue());
                tagRecord.setTagClass(tag.getClass().getSimpleName());
                tagRecord.setTagValue(UUID.fromString(tag.getValue().toString()));
                tagRecord.store();
            });
        });
    }

    private EventStream query(BricksetId bricksetId) {
        final List<io.bricksets.domain.event.Event> events = new ArrayList<>();
        var records = context.selectFrom(Event.EVENT)
                .where(row(Event.EVENT.ID).in(
                        select(Tag.TAG.EVENT_ID)
                                .from(Tag.TAG)
                                .where(Tag.TAG.TAG_CLASS.eq(BricksetId.class.getSimpleName()))
                                .and(Tag.TAG.TAG_VALUE.eq(bricksetId.getValue()))
                ))
                .orderBy(Event.EVENT.POSITION.desc())
                .fetch();
        records.forEach(it -> events.add(EventMapper.INSTANCE.deserialize(it.getEventValue(), it.getEventClass())));
        return new EventStream(events);
    }
}