package io.bricksets.domain.aggregate;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.vocabulary.domain.event.EventId;

import static java.util.Collections.emptyList;

public abstract class EventSourcedAggregate implements Aggregate {

    private final EventStream statusQuo = new EventStream(emptyList());
    private final EventStream mutations = new EventStream(emptyList());

    public EventSourcedAggregate() {
    }

    public EventSourcedAggregate(final EventStream statusQuo) {
        statusQuo.absorb(statusQuo);
        statusQuo.events().forEach(this::when);
    }

    public EventId getStatusQuoPointer() {
        return statusQuo.getLastEventId();
    }

    public EventStream getMutations() {
        return mutations;
    }

    protected void apply(Event event) {
        when(event);
        mutations.events().add(event);
    }

    protected abstract void when(Event event);
}