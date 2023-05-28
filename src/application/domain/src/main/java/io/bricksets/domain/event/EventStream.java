package io.bricksets.domain.event;

import io.bricksets.vocabulary.domain.event.EventId;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

public record EventStream(List<Event> events) {

    public EventStream {
        events = isNull(events) ? emptyList() : new ArrayList<>(events);
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public EventId getLastEventId() {
        return events.stream()
                .reduce((first, last) -> last)
                .map(Event::id)
                .orElseThrow();
    }

    public boolean containsEventOfType(final Class<? extends Event> clazz) {
        return events.stream()
                .map(Event::getClass)
                .anyMatch(it -> it.equals(clazz));
    }

    public void copy(final EventStream eventStream) {
        this.events.clear();
        this.events.addAll(eventStream.events());
    }
}