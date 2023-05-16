package io.bricksets.domain.brickset.event;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetCreated(EventId id, Timestamp occurredOn, Set<AggregateId> tags, BricksetId bricksetId, BricksetNumber number, BricksetTitle title) implements Event {
    public BricksetCreated(Timestamp occurredAt, BricksetId bricksetId, BricksetNumber number, BricksetTitle title) {
        this(EventId.createNew(), occurredAt, Set.of(bricksetId), bricksetId, number, title);
    }
}