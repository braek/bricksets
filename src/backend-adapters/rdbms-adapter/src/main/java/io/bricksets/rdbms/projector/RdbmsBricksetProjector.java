package io.bricksets.rdbms.projector;

import io.bricksets.domain.brickset.BricksetProjector;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetModified;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import org.jooq.DSLContext;

public class RdbmsBricksetProjector extends RdbmsBaseProjector implements BricksetProjector {

    public RdbmsBricksetProjector(DSLContext dsl) {
        super(dsl);
    }

    @Override
    public void project(BricksetCreated bricksetCreated) {

    }

    @Override
    public void project(BricksetModified bricksetModified) {

    }

    @Override
    public void project(BricksetRemoved bricksetRemoved) {

    }
}