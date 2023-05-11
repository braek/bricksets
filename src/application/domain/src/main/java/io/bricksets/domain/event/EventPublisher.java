package io.bricksets.domain.event;

import java.util.List;

public interface EventPublisher {

    void publish(Event event);

    void publish(List<Event> events);
}