package be.koder.bricksets.domain.event;

public interface EventHandler {
    void handle(Event event);
}