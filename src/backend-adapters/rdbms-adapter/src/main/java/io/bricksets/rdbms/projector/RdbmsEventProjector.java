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
        if (event instanceof BricksetCreated theEvent) {
            var record = dsl.newRecord(BRICKSET);
            record.setId(theEvent.bricksetId().getValue());
            record.setTitle(theEvent.title().getValue());
            record.setNumber(theEvent.number().getValue());
            record.setCreatedOn(theEvent.occurredOn().toLocalDateTime());
            record.store();
        }
        if (event instanceof BricksetModified theEvent) {
            final var record = dsl.fetchSingle(BRICKSET, BRICKSET.ID.eq(theEvent.bricksetId().getValue()));
            record.setTitle(theEvent.title().getValue());
            record.setModifiedOn(theEvent.occurredOn().toLocalDateTime());
            record.store();
        }
        if (event instanceof BricksetRemoved theEvent) {
            final var record = dsl.fetchSingle(BRICKSET, BRICKSET.ID.eq(theEvent.bricksetId().getValue()));
            record.delete();
        }
        throw new IllegalArgumentException(String.format("Cannot project this type of Event (%s)", event.getClass().getSimpleName()));
    }
}