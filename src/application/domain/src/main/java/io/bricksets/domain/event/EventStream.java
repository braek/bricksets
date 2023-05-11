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
        if (!isEmpty()) {
            return null;
        }
        return events.get(events.size() - 1).id();
    }

    public boolean containsInstanceOf(final Class<? extends Event> clazz) {
        return events.stream()
                .map(Event::getClass)
                .anyMatch(it -> it.equals(clazz));
    }
}