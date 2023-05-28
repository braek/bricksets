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
        this.statusQuo.events().forEach(this::dispatch);
    }

    public final EventId getLastEventId() {
        return statusQuo.getLastEventId();
    }

    public final EventStream getMutations() {
        return mutations;
    }

    public final boolean hasNoMutations() {
        return mutations.isEmpty();
    }

    public final boolean isNotNew() {
        return !statusQuo.isEmpty();
    }

    protected final void apply(final Event event) {
        dispatch(event);
        mutations.events().add(event);
    }

    public final boolean isNotEqualTo(final EventStream eventStream) {
        return !statusQuo.equals(eventStream);
    }

    protected abstract void dispatch(final Event event);
}