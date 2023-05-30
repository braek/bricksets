package io.bricksets.rdbms.repository;

import io.bricksets.domain.aggregate.EventSourcedAggregate;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockingException;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.rdbms.tables.records.EventRecord;
import io.bricksets.vocabulary.domain.AggregateId;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.bricksets.rdbms.Tables.EVENT;
import static io.bricksets.rdbms.Tables.TAG;
import static org.jooq.impl.DSL.*;

public abstract class RdbmsBaseRepository {

    protected final DSLContext dsl;

    public RdbmsBaseRepository(final DSLContext dsl) {
        this.dsl = dsl;
    }

    protected final EventStream getEventStream(Class<? extends Event>... eventTypes) {
        final var eventTypesToFilter = Arrays.stream(eventTypes).toList();
        final List<Event> events = new ArrayList<>();
        final var filter = noCondition();
        eventTypesToFilter.forEach(it -> filter.or(EVENT.EVENT_CLASS.eq(it.getSimpleName())));
        var records = dsl.selectFrom(EVENT)
                .where(filter)
                .orderBy(EVENT.POSITION.asc())
                .fetch();
        records.forEach(record -> events.add(mapEvent(record)));
        return new EventStream(events);
    }

    protected final EventStream getEventStream(final AggregateId aggregateId) {
        final List<Event> events = new ArrayList<>();
        var records = dsl.selectFrom(EVENT)
                .where(row(EVENT.ID).in(
                        select(TAG.EVENT_ID)
                                .from(TAG)
                                .where(TAG.TAG_CLASS.eq(aggregateId.getClass().getSimpleName()))
                                .and(TAG.TAG_VALUE.eq(aggregateId.toString()))
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

    @Transactional
    protected void save(final EventSourcedAggregate aggregate) {

        // No mutations: do nothing
        if (aggregate.hasNoMutations()) {
            return;
        }

        // Optimistic locking
        var persisted = getEventStream(aggregate.getId());
        if (aggregate.isNotEqualTo(persisted)) {
            throw new EventStreamOptimisticLockingException(aggregate.getLastEventId());
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
                tagRecord.setTagValue(tag.toString());
                tagRecord.store();
            });
        });
    }
}