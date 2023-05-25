package io.bricksets.rdbms;

import io.bricksets.domain.aggregate.EventSourcedAggregate;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockingException;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.rdbms.tables.records.EventsRecord;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;
import org.jooq.DSLContext;

import java.util.*;

import static io.bricksets.rdbms.Tables.EVENTS;
import static io.bricksets.rdbms.tables.Tags.TAGS;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

public abstract class RdbmsBaseRepository {

    protected final DSLContext dsl;

    public RdbmsBaseRepository(final DSLContext dsl) {
        this.dsl = dsl;
    }

    protected EventStream getEventStream(final AggregateId aggregateId) {
        final List<Event> events = new ArrayList<>();
        var records = dsl.selectFrom(EVENTS)
                .where(row(EVENTS.ID).in(
                        select(TAGS.EVENT_ID)
                                .from(TAGS)
                                .where(TAGS.TAG_CLASS.eq(aggregateId.getClass().getSimpleName()))
                                .and(TAGS.TAG_VALUE.eq((UUID) aggregateId.getValue()))
                ))
                .orderBy(EVENTS.POSITION.asc())
                .fetch();
        records.forEach(record -> events.add(mapEvent(record)));
        return new EventStream(events);
    }

    private Event mapEvent(final EventsRecord record) {
        var partial = EventMapper.INSTANCE.deserialize(record.getEventValue(), record.getEventClass());
        if (partial instanceof BricksetCreated created) {
            return new BricksetCreated(
                    EventId.fromUuid(record.getId()),
                    Timestamp.fromLocalDateTime(record.getOccurredOn()),
                    Set.of(created.bricksetId()),
                    created.bricksetId(),
                    created.number(),
                    created.title()
            );
        }
        throw new IllegalStateException("Cannot map this event for persistence");
    }

    protected void save(final EventSourcedAggregate aggregate) {

        // No mutations
        if (aggregate.isStatusQuo()) {
            return;
        }

        // Optimistic locking
        var persistedEventStream = getEventStream(aggregate.getId());
        if (!Objects.equals(aggregate.getStatusQuoPointer(), persistedEventStream.getPointer())) {
            throw new EventStreamOptimisticLockingException(aggregate.getStatusQuoPointer());
        }

        // Persist mutations
        persist(aggregate.getMutations());
    }

    private void persist(final EventStream mutations) {
        mutations.events().forEach(event -> {

            // Store event
            var eventRecord = dsl.newRecord(EVENTS);
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