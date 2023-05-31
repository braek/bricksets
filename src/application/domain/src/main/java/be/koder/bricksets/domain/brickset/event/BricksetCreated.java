package be.koder.bricksets.domain.brickset.event;

import be.koder.bricksets.domain.event.Event;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.domain.AggregateId;
import be.koder.bricksets.vocabulary.domain.event.EventId;
import be.koder.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

public record BricksetCreated(EventId id, Timestamp occurredOn, Set<AggregateId> tags, BricksetNumber number, BricksetTitle title) implements Event {

    public BricksetCreated(Timestamp occurredOn, BricksetId bricksetId, BricksetNumber number, BricksetTitle title) {
        this(EventId.createNew(), occurredOn, Set.of(bricksetId), number, title);
    }

    public BricksetId bricksetId() {
        return tags.stream()
                .filter(BricksetId.class::isInstance)
                .findFirst()
                .map(BricksetId.class::cast)
                .orElseThrow();
    }
}