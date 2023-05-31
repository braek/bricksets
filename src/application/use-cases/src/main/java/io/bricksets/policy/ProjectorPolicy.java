package io.bricksets.policy;

import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventHandler;
import io.bricksets.domain.event.EventProjector;

public final class ProjectorPolicy implements EventHandler {

    private final EventProjector eventProjector;

    public ProjectorPolicy(EventProjector eventProjector) {
        this.eventProjector = eventProjector;
    }

    @Override
    public void handle(Event event) {
        eventProjector.project(event);
    }
}