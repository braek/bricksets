package be.koder.bricksets.domain.brickset.event;

import be.koder.bricksets.domain.event.Event;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.domain.AggregateId;
import be.koder.bricksets.vocabulary.domain.event.EventId;
import be.koder.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetRemoved(EventId id, Timestamp occurredOn, Set<AggregateId> tags) implements Event {

    public BricksetRemoved(Timestamp occurredOn, BricksetId bricksetId) {
        this(EventId.createNew(), occurredOn, Set.of(bricksetId));
    }

    public BricksetId bricksetId() {
        return tags.stream()
                .filter(BricksetId.class::isInstance)
                .findFirst()
                .map(BricksetId.class::cast)
                .orElseThrow();
    }
}