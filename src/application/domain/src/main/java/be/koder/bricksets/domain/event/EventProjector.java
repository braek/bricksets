package be.koder.bricksets.domain.event;

public interface EventProjector {
    void project(Event event);
}