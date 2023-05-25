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
        this.statusQuo.copy(statusQuo);
        this.statusQuo.events().forEach(this::when);
    }

    public EventId getStatusQuoPointer() {
        return statusQuo.getPointer();
    }

    public EventStream getMutations() {
        return mutations;
    }

    public boolean inStatusQuo() {
        return mutations.isEmpty();
    }

    public boolean isExistingAggregate() {
        return !statusQuo.isEmpty();
    }

    protected void apply(Event event) {
        when(event);
        mutations.events().add(event);
    }

    protected abstract void when(Event event);
}