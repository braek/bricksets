package io.bricksets.domain.aggregate;

import io.bricksets.domain.event.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class EventSourcedAggregate implements Aggregate {

    private final List<Event> mutatingEvents = new ArrayList<>();

    public List<Event> getMutatingEvents() {
        return mutatingEvents;
    }
}