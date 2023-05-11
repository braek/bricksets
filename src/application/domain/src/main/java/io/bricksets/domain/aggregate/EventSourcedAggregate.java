package io.bricksets.domain.aggregate;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;

import java.util.ArrayList;
import java.util.List;

public abstract class EventSourcedAggregate implements Aggregate {

    private final List<Event> mutatingEvents = new ArrayList<>();

    public EventSourcedAggregate() {}

    public EventSourcedAggregate(EventStream eventStream) {
        eventStream.events().forEach(this::when);
    }

    public List<Event> getMutatingEvents() {
        return mutatingEvents;
    }

    protected abstract void when(Event event);

    protected void apply(Event event) {
        mutatingEvents.add(event);
    }
}