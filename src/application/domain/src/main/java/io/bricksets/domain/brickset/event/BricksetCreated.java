package io.bricksets.domain.brickset.event;

import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetCreated(EventId id, Timestamp occurredOn, Set<AggregateId> tags, BricksetNumber number, BricksetTitle title) implements Event {

    public BricksetCreated(Timestamp occurredOn, BricksetId bricksetId, BricksetNumber number, BricksetTitle title) {
        this(EventId.createNew(), occurredOn, Set.of(bricksetId), number, title);
    }

    public BricksetId bricksetId() {
        return tags.stream()
                .filter(it -> it instanceof BricksetId)
                .map(BricksetId.class::cast)
                .findFirst()
                .orElseThrow();
    }
}