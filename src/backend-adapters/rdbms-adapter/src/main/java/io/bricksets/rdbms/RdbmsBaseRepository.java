package io.bricksets.rdbms;

import io.bricksets.domain.aggregate.EventSourcedAggregate;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockingException;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.rdbms.tables.records.EventRecord;
import io.bricksets.vocabulary.domain.AggregateId;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static io.bricksets.rdbms.Tables.EVENT;
import static io.bricksets.rdbms.Tables.TAG;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

public abstract class RdbmsBaseRepository {

    protected final DSLContext dsl;

    public RdbmsBaseRepository(final DSLContext dsl) {
        this.dsl = dsl;
    }

    protected EventStream getEventStream(final AggregateId aggregateId) {
        final List<Event> events = new ArrayList<>();
        var records = dsl.selectFrom(EVENT)
                .where(row(EVENT.ID).in(
                        select(TAG.EVENT_ID)
                                .from(TAG)
                                .where(TAG.TAG_CLASS.eq(aggregateId.getClass().getSimpleName()))
                                .and(TAG.TAG_VALUE.eq((UUID) aggregateId.getValue()))
                ))
                .orderBy(EVENT.POSITION.asc())
                .fetch();
        records.forEach(record -> events.add(mapEvent(record)));
        return new EventStream(events);
    }

    private Event mapEvent(final EventRecord event) {
        var tags = dsl.selectFrom(TAG)
                .where(TAG.EVENT_ID.eq(event.getId()))
                .fetch();
        return EventMapper.INSTANCE.map(event, tags);
    }

    protected void save(final EventSourcedAggregate aggregate) {

        // No mutations
        if (aggregate.inStatusQuo()) {
            return;
        }

        // Optimistic locking
        if (!aggregate.hasStatusQuo()) {
            var persistedEventStream = getEventStream(aggregate.getId());
            if (!Objects.equals(aggregate.getStatusQuoPointer(), persistedEventStream.getPointer())) {
                throw new EventStreamOptimisticLockingException(aggregate.getStatusQuoPointer());
            }
        }

        // Persist mutations
        persist(aggregate.getMutations());
    }

    private void persist(final EventStream mutations) {
        mutations.events().forEach(event -> {

            // Store event
            var eventRecord = dsl.newRecord(EVENT);
            eventRecord.setId(event.id().getValue());
            eventRecord.setOccurredOn(event.occurredOn().toLocalDateTime());
            eventRecord.setEventClass(event.getClass().getSimpleName());
            eventRecord.setEventValue(EventMapper.INSTANCE.serialize(event));
            eventRecord.store();

            // Store tags
            event.tags().forEach(tag -> {
                var tagRecord = dsl.newRecord(TAG);
                tagRecord.setEventId(event.id().getValue());
                tagRecord.setTagClass(tag.getClass().getSimpleName());
                tagRecord.setTagValue(UUID.fromString(tag.getValue().toString()));
                tagRecord.store();
            });
        });
    }
}