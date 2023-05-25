package io.bricksets.rdbms;

import io.bricksets.domain.event.EventStream;
import io.bricksets.vocabulary.domain.AggregateId;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional
public abstract class RdbmsBaseRepository {

    protected final DSLContext dsl;

    public RdbmsBaseRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public EventStream query(final AggregateId aggregateId) {
        return new EventStream(Collections.emptyList());
    }
}