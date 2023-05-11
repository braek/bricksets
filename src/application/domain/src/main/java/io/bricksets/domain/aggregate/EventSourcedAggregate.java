package io.bricksets.domain.aggregate;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamEmptyException;
import io.bricksets.vocabulary.domain.event.EventId;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public abstract class EventSourcedAggregate implements Aggregate {

    private final List<Event> mutatingEvents = new ArrayList<>();
    private EventId lastEventId;

    public EventSourcedAggregate() {
    }

    public EventSourcedAggregate(EventStream eventStream) {
        if (eventStream.isEmpty()) {
            throw new EventStreamEmptyException(getClass());
        }
        eventStream.events().forEach(this::when);
        lastEventId = eventStream.getLastEventId();
    }

    public boolean isNew() {
        return isNull(lastEventId);
    }

    public EventId getLastEventId() {
        return lastEventId;
    }

    public List<Event> getMutatingEvents() {
        return mutatingEvents;
    }

    protected void apply(Event event) {
        when(event);
        mutatingEvents.add(event);
    }

    protected abstract void when(Event event);
}