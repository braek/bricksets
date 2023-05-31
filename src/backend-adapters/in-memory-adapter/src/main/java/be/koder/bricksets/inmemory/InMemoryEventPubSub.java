package be.koder.bricksets.inmemory;

import be.koder.bricksets.domain.event.*;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class InMemoryEventPubSub implements EventPublisher, EventSubscriber {

    private final List<Event> events = new ArrayList<>();
    private final List<EventHandler> handlers = new ArrayList<>();

    public void verifyEvents(final List<Event> expectedEvents) {
        final RecursiveComparisonConfiguration config = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id")
                .build();
        assertThat(events).usingRecursiveComparison(config).isEqualTo(expectedEvents);
    }

    @Override
    public void publish(final Event event) {
        events.add(event);
        handlers.forEach(it -> it.handle(event));
    }

    @Override
    public void publish(final List<Event> events) {
        events.forEach(this::publish);
    }

    @Override
    public void publish(final EventStream eventStream) {
        publish(eventStream.events());
    }

    @Override
    public void subscribe(final EventHandler handler) {
        handlers.add(handler);
    }
}