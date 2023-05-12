package io.bricksets.domain.event;

import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

// Marker interface for domain events
public interface Event {

    EventId id();

    Timestamp occurredAt();

    Set<AggregateId> tags();
}