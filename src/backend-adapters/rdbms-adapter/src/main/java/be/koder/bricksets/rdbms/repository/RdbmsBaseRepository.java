package be.koder.bricksets.rdbms.repository;

import be.koder.bricksets.domain.aggregate.EventSourcedAggregate;
import be.koder.bricksets.domain.event.Event;
import be.koder.bricksets.domain.event.EventStream;
import be.koder.bricksets.domain.event.EventStreamOptimisticLockingException;
import be.koder.bricksets.rdbms.mapper.EventMapper;
import be.koder.bricksets.rdbms.tables.records.EventRecord;
import be.koder.bricksets.vocabulary.domain.AggregateId;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static be.koder.bricksets.rdbms.Tables.EVENT;
import static be.koder.bricksets.rdbms.Tables.TAG;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

@Transactional
public abstract class RdbmsBaseRepository {

    protected final DSLContext dsl;

    public RdbmsBaseRepository(final DSLContext dsl) {
        this.dsl = dsl;
    }

    @SafeVarargs
    protected final EventStream openEventStream(final Class<? extends Event>... eventTypes) {
        final var filter = Arrays.stream(eventTypes)
                .map(Class::getSimpleName)
                .collect(Collectors.toSet());
        final var records = dsl.selectFrom(EVENT)
                .where(EVENT.TYPE.in(filter))
                .orderBy(EVENT.POSITION.asc())
                .fetch();
        final List<Event> events = new ArrayList<>();
        records.forEach(record -> events.add(mapEvent(record)));
        return new EventStream(events);
    }

    protected final EventStream openEventStream(final AggregateId aggregateId) {
        final List<Event> events = new ArrayList<>();
        var records = dsl.selectFrom(EVENT)
                .where(row(EVENT.ID).in(
                        select(TAG.EVENT_ID)
                                .from(TAG)
                                .where(TAG.TYPE.eq(aggregateId.getClass().getSimpleName()))
                                .and(TAG.VALUE.eq(aggregateId.toString()))
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

        // No mutations: do nothing
        if (aggregate.hasNoMutations()) {
            return;
        }

        // Optimistic locking
        var persisted = openEventStream(aggregate.getId());
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
            eventRecord.setType(event.getClass().getSimpleName());
            eventRecord.setContent(EventMapper.INSTANCE.serialize(event));
            eventRecord.store();

            // Store tags
            event.tags().forEach(tag -> {
                var tagRecord = dsl.newRecord(TAG);
                tagRecord.setEventId(event.id().getValue());
                tagRecord.setType(tag.getClass().getSimpleName());
                tagRecord.setValue(tag.toString());
                tagRecord.store();
            });
        });
    }
}