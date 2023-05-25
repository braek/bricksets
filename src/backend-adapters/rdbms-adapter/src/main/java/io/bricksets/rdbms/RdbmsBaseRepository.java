package io.bricksets.rdbms;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.vocabulary.domain.AggregateId;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.bricksets.rdbms.Tables.EVENTS;
import static io.bricksets.rdbms.tables.Tags.TAGS;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

@Transactional
public abstract class RdbmsBaseRepository {

    protected final DSLContext dsl;

    public RdbmsBaseRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public EventStream getEventStream(final AggregateId aggregateId) {
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
        // TODO: revise deserialization
        records.forEach(it -> events.add(EventMapper.INSTANCE.deserialize(it.getEventValue(), it.getEventClass())));
        return new EventStream(events);
    }
}