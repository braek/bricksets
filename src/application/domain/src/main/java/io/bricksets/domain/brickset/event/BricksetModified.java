package io.bricksets.domain.brickset.event;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetModified(EventId id, Timestamp occurredOn, Set<AggregateId> tags, BricksetId bricksetId, BricksetTitle title) implements Event {
    public BricksetModified(Timestamp occurredAt, BricksetId bricksetId, BricksetTitle title) {
        this(EventId.createNew(), occurredAt, Set.of(bricksetId), bricksetId, title);
    }
}