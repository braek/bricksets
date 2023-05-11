package io.bricksets.domain.brickset;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetModified(EventId id, Timestamp timestamp, Set<AggregateId> tags, BricksetId bricksetId, BricksetTitle title) implements Event {
    public BricksetModified(BricksetId bricksetId, BricksetTitle title) {
        this(EventId.createNew(), Timestamp.now(), Set.of(bricksetId), bricksetId, title);
    }
}