package io.bricksets.vocabulary.brickset;

public final class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String str) {
        super(String.format("Cannot create title from string %s", str));
    }
}