package io.bricksets.domain.event;

import io.bricksets.domain.aggregate.EventSourcedAggregate;

public final class EventStreamEmptyException extends RuntimeException {
    public EventStreamEmptyException(Class<? extends EventSourcedAggregate> clazz) {
        super(String.format("You cannot build an '%s' aggregate from an empty EventStream!", clazz.getSimpleName()));
    }
}