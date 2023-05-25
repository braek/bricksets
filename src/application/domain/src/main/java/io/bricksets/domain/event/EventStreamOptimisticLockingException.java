package io.bricksets.domain.event;

import io.bricksets.vocabulary.domain.event.EventId;

public final class EventStreamOptimisticLockingException extends RuntimeException {
    public EventStreamOptimisticLockingException(EventId lastEventId) {
        super(String.format("Optimistic lock: state of the aggregate changed since event with ID %s", lastEventId));
    }
}