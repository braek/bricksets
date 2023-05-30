package io.bricksets.domain.event;

public interface EventProjector {
    void project(Event event);
}