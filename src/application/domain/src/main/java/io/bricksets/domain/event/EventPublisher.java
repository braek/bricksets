package io.bricksets.domain.event;

public interface EventPublisher {
    void publish(Event event);
}