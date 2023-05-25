package io.bricksets.rdbms;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventStream;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.rdbms.tables.Events;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.bricksets.rdbms.Tables.EVENTS;
import static io.bricksets.rdbms.tables.Tags.TAGS;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

public class RdbmsBricksetRepository extends RdbmsBaseRepository implements BricksetRepository, BricksetNumberService {

    public RdbmsBricksetRepository(final DSLContext context) {
        super(context);
    }

    @Override
    public boolean exists(final BricksetNumber number) {
        return false;
    }

    @Override
    public Optional<Brickset> get(final BricksetId bricksetId) {
        var eventStream = query(bricksetId);
        if (eventStream.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Brickset(eventStream));
    }

    @Override
    public void save(final Brickset brickset) {
        var mutations = brickset.getMutations();

        // No mutations
        if (mutations.isEmpty()) {
            return;
        }

        // Optimistic locking
        var latestState = query(brickset.getId());
        brickset.evaluateOptimisticLocking(latestState);

        mutations.events().forEach(event -> {

            // Store event
            var eventRecord = dsl.newRecord(Events.EVENTS);
            eventRecord.setId(event.id().getValue());
            eventRecord.setOccurredOn(event.occurredOn().toLocalDateTime());
            eventRecord.setEventClass(event.getClass().getSimpleName());
            eventRecord.setEventValue(EventMapper.INSTANCE.serialize(event));
            eventRecord.store();

            // Store tags
            event.tags().forEach(tag -> {
                var tagRecord = dsl.newRecord(TAGS);
                tagRecord.setEventId(event.id().getValue());
                tagRecord.setTagClass(tag.getClass().getSimpleName());
                tagRecord.setTagValue(UUID.fromString(tag.getValue().toString()));
                tagRecord.store();
            });
        });
    }

    private EventStream query(final BricksetId bricksetId) {
        final List<io.bricksets.domain.event.Event> events = new ArrayList<>();
        var records = dsl.selectFrom(EVENTS)
                .where(row(EVENTS.ID).in(
                        select(TAGS.EVENT_ID)
                                .from(TAGS)
                                .where(TAGS.TAG_CLASS.eq(BricksetId.class.getSimpleName()))
                                .and(TAGS.TAG_VALUE.eq(bricksetId.getValue()))
                ))
                .orderBy(EVENTS.POSITION.asc())
                .fetch();
        // TODO: revise deserialization
        records.forEach(it -> events.add(EventMapper.INSTANCE.deserialize(it.getEventValue(), it.getEventClass())));
        return new EventStream(events);
    }
}