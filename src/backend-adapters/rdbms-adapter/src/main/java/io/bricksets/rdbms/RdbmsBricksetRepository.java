package io.bricksets.rdbms;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import io.bricksets.domain.event.EventStreamOptimisticLockingException;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.rdbms.tables.Events;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import org.jooq.DSLContext;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static io.bricksets.rdbms.tables.Tags.TAGS;

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
        var eventStream = getEventStream(bricksetId);
        if (eventStream.isEmpty() || eventStream.containsEventOfType(BricksetRemoved.class)) {
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
        var persistedState = getEventStream(brickset.getId());
        if (!Objects.equals(brickset.getStatusQuoPointer(), persistedState.getPointer())) {
            throw new EventStreamOptimisticLockingException(brickset.getStatusQuoPointer());
        }

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
}