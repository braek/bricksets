package io.bricksets.domain.brickset;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

public record BricksetModified(EventId id, Timestamp timestamp, BricksetId bricksetId, BricksetTitle title) implements Event {
    public BricksetModified(BricksetId bricksetId, BricksetTitle title) {
        this(EventId.createNew(), Timestamp.now(), bricksetId, title);
    }
}