package io.bricksets.domain.event;

public final class EventStreamEmptyException extends RuntimeException {
    public EventStreamEmptyException() {
        super("You cannot build  from an empty EventStream!");
    }
}