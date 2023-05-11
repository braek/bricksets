package io.bricksets.test;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventPublisher;

import java.util.ArrayList;
import java.util.List;

public final class InMemoryEventPublisher implements EventPublisher {

    private final List<Event> events = new ArrayList<>();

    public void verifyEvents(List<Event> events) {
        
    }

    @Override
    public void publish(Event event) {
        events.add(event);
    }

    @Override
    public void publish(List<Event> events) {
        events.forEach(this::publish);
    }
}