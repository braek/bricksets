package io.bricksets.domain.brickset;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetRemoved(EventId id, Timestamp timestamp, Set<AggregateId> tags, BricksetId bricksetId, BricksetNumber number) implements Event {
    public BricksetRemoved(BricksetId bricksetId, BricksetNumber number) {
        this(EventId.createNew(), Timestamp.now(), Set.of(bricksetId), bricksetId, number);
    }
}