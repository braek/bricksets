package be.koder.bricksets.domain.event;

import be.koder.bricksets.vocabulary.domain.event.EventId;

public final class EventStreamOptimisticLockingException extends RuntimeException {
    public EventStreamOptimisticLockingException(EventId lastEventId) {
        super(String.format("Optimistic locking: aggregate status quo changed since last event (%s)", lastEventId));
    }
}