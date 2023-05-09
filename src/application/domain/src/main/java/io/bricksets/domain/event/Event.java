package io.bricksets.domain.event;

import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

public interface Event {

    EventId id();

    Timestamp timestamp();
}