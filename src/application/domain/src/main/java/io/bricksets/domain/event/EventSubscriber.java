package io.bricksets.domain.event;

public interface EventSubscriber {
    void subscribe(EventHandler handler);
}