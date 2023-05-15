package io.bricksets.domain.brickset;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetRemoved(EventId id, Timestamp occurred, Set<AggregateId> tags, BricksetId bricksetId) implements Event {
    public BricksetRemoved(Timestamp occurredAt, BricksetId bricksetId) {
        this(EventId.createNew(), occurredAt, Set.of(bricksetId), bricksetId);
    }
}