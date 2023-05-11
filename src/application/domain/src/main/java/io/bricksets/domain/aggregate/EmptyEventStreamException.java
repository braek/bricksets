package io.bricksets.domain.aggregate;

public final class EmptyEventStreamException extends RuntimeException {
    public EmptyEventStreamException(Class<? extends EventSourcedAggregate> clazz) {
        super(String.format("You cannot build an '%s' aggregate from an empty EventStream!", clazz.getSimpleName()));
    }
}