package io.bricksets.rdbms.projector;

import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class RdbmsBaseProjector {

    public RdbmsBaseProjector(DSLContext dsl) {
        this.dsl = dsl;
    }

    protected final DSLContext dsl;
}