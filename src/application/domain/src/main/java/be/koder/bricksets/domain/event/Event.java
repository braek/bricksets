package be.koder.bricksets.domain.event;

import be.koder.bricksets.vocabulary.domain.AggregateId;
import be.koder.bricksets.vocabulary.domain.event.EventId;
import be.koder.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

// Marker interface for domain events
public interface Event {

    EventId id();

    Timestamp occurredOn();

    Set<AggregateId> tags();
}