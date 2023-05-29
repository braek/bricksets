package io.bricksets.vocabulary.brickset;

public final class InvalidBricksetTitleException extends RuntimeException {
    public InvalidBricksetTitleException(String str) {
        super(String.format("Cannot create Brickset Title from string (%s)", str));
    }
}