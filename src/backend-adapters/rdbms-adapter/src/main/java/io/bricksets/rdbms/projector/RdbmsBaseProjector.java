package io.bricksets.rdbms.projector;

import org.jooq.DSLContext;

public abstract class RdbmsBaseProjector {

    public RdbmsBaseProjector(DSLContext dsl) {
        this.dsl = dsl;
    }

    protected final DSLContext dsl;
}