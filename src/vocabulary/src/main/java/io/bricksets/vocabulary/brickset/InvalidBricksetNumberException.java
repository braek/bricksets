package io.bricksets.vocabulary.brickset;

public final class InvalidBricksetNumberException extends RuntimeException {
    public InvalidBricksetNumberException(final String str) {
        super(String.format("Cannot create Brickset Number from string %s", str));
    }
}