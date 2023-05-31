package be.koder.bricksets.policy;

import be.koder.bricksets.domain.event.Event;
import be.koder.bricksets.domain.event.EventHandler;
import be.koder.bricksets.domain.event.EventProjector;

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