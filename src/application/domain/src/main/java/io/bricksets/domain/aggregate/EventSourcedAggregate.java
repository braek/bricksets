package io.bricksets.domain.aggregate;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockingException;
import io.bricksets.vocabulary.domain.event.EventId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

public abstract class EventSourcedAggregate implements Aggregate {

    private final EventStream currentState = new EventStream(emptyList());
    private final List<Event> mutatingEvents = new ArrayList<>();

    public EventSourcedAggregate() {
    }

    public EventSourcedAggregate(final EventStream eventStream) {
        eventStream.copy(eventStream);
        eventStream.events().forEach(this::when);
    }

    public void evaluateOptimisticLocking(final EventStream latestState) {
        if (!Objects.equals(currentState.getLastEventId(), latestState.getLastEventId())) {
            throw new EventStreamOptimisticLockingException(currentState.getLastEventId());
        }
    }

    public EventId getLastEventId() {
        return currentState.getLastEventId();
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