package io.bricksets.rdbms.projector;

import io.bricksets.domain.brickset.BricksetProjector;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetModified;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import org.jooq.DSLContext;

import static io.bricksets.rdbms.Tables.BRICKSET;

public class RdbmsBricksetProjector extends RdbmsBaseProjector implements BricksetProjector {

    public RdbmsBricksetProjector(DSLContext dsl) {
        super(dsl);
    }

    @Override
    public void project(BricksetCreated event) {
        final var record = dsl.newRecord(BRICKSET);
        record.setId(event.bricksetId().getValue());
        record.setTitle(event.title().getValue());
        record.setNumber(event.number().getValue());
        record.setCreatedOn(event.occurredOn().toLocalDateTime());
        record.store();
    }

    @Override
    public void project(BricksetModified event) {
        final var record = dsl.fetchSingle(BRICKSET, BRICKSET.ID.eq(event.bricksetId().getValue()));
        record.setTitle(event.title().getValue());
        record.setModifiedOn(event.occurredOn().toLocalDateTime());
        record.store();
    }

    @Override
    public void project(BricksetRemoved event) {
        final var record = dsl.fetchSingle(BRICKSET, BRICKSET.ID.eq(event.bricksetId().getValue()));
        record.delete();
    }
}