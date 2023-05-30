package io.bricksets.inmemory;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventHandler;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.domain.event.EventStream;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class InMemoryEventPublisher implements EventPublisher {

    private final List<Event> events = new ArrayList<>();
    private final List<EventHandler> handlers = new ArrayList<>();

    public void verifyEvents(List<Event> expectedEvents) {
        final RecursiveComparisonConfiguration config = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id")
                .build();
        assertThat(events).usingRecursiveComparison(config).isEqualTo(expectedEvents);
    }

    public void addSubscriber(EventHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void publish(Event event) {
        events.add(event);
        handlers.forEach(it -> it.handle(event));
    }

    @Override
    public void publish(List<Event> events) {
        events.forEach(this::publish);
    }

    @Override
    public void publish(EventStream eventStream) {
        publish(eventStream.events());
    }
}