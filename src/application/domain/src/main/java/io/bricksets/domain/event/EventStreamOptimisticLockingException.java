package io.bricksets.domain.event;

import io.bricksets.vocabulary.domain.event.EventId;

public final class EventStreamOptimisticLockingException extends RuntimeException {
    public EventStreamOptimisticLockingException(EventId statusQuoPointer) {
        super(String.format("Optimistic lock: aggregate status quo changed (%s)", statusQuoPointer));
    }
}