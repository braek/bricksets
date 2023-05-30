package io.bricksets.rdbms.projector;

import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetModified;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventProjector;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

import static io.bricksets.rdbms.Tables.BRICKSET;

@Transactional
public class RdbmsEventProjector implements EventProjector {

    private final DSLContext dsl;

    public RdbmsEventProjector(final DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void project(final Event event) {
        if (event instanceof BricksetCreated concrete) {
            var record = dsl.newRecord(BRICKSET);
            record.setId(concrete.bricksetId().getValue());
            record.setTitle(concrete.title().getValue());
            record.setNumber(concrete.number().getValue());
            record.setCreatedOn(concrete.occurredOn().toLocalDateTime());
            record.setModifiedOn(concrete.occurredOn().toLocalDateTime());
            record.store();
            return;
        }
        if (event instanceof BricksetModified concrete) {
            final var record = dsl.fetchSingle(BRICKSET, BRICKSET.ID.eq(concrete.bricksetId().getValue()));
            record.setTitle(concrete.title().getValue());
            record.setModifiedOn(concrete.occurredOn().toLocalDateTime());
            record.store();
            return;
        }
        if (event instanceof BricksetRemoved concrete) {
            final var record = dsl.fetchSingle(BRICKSET, BRICKSET.ID.eq(concrete.bricksetId().getValue()));
            record.delete();
            return;
        }
        throw new IllegalArgumentException(String.format("Cannot project this type of Event (%s)", event.getClass().getSimpleName()));
    }
}