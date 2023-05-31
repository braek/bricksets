package be.koder.bricksets.vocabulary.brickset;

public final class InvalidBricksetNumberException extends RuntimeException {
    public InvalidBricksetNumberException(final String str) {
        super(String.format("Cannot create BricksetNumber from string (%s)", str));
    }
}