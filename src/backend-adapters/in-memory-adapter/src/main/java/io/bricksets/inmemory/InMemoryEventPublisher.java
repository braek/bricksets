package io.bricksets.inmemory;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.domain.event.EventStream;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public final class InMemoryEventPublisher implements EventPublisher {

    private final List<Event> events = new ArrayList<>();

    public void verifyEvents(List<Event> expectedEvents) {
        final RecursiveComparisonConfiguration config = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id")
                .build();
        assertThat(events).usingRecursiveComparison(config).isEqualTo(expectedEvents);
    }

    @Override
    public void publish(Event event) {
        events.add(event);
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