package io.bricksets.domain.event;

import io.bricksets.vocabulary.domain.event.EventId;

public final class EventStreamOptimisticLockingException extends RuntimeException {
    public EventStreamOptimisticLockingException(EventId lastEventId) {
        super(String.format("Optimistic locking: aggregate status quo changed since last event (%s)", lastEventId));
    }
}