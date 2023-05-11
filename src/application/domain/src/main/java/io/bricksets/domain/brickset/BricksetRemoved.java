package io.bricksets.domain.brickset;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

public record BricksetRemoved(EventId id, Timestamp timestamp, BricksetId bricksetId) implements Event {
    public BricksetRemoved(BricksetId bricksetId) {
        this(EventId.createNew(), Timestamp.now(), bricksetId);
    }
}