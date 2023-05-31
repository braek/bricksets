package be.koder.bricksets.vocabulary.brickset;

import be.koder.bricksets.vocabulary.domain.ValueObject;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public final class BricksetTitle implements ValueObject {

    private final String value;

    private BricksetTitle(final String str) {
        final var sanitized = ofNullable(str)
                .map(String::trim)
                .map(it -> it.replace("\n", ""))
                .orElse(null);
        if (isNull(sanitized) || sanitized.length() > 100) {
            throw new InvalidBricksetTitleException(str);
        }
        this.value = sanitized;
    }

    public static BricksetTitle fromString(final String str) {
        return new BricksetTitle(str);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BricksetTitle title = (BricksetTitle) o;
        return Objects.equals(getValue(), title.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
