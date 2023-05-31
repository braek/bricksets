package be.koder.bricksets.vocabulary.brickset;

public final class InvalidBricksetTitleException extends RuntimeException {
    public InvalidBricksetTitleException(String str) {
        super(String.format("Cannot create BricksetTitle from string (%s)", str));
    }
}