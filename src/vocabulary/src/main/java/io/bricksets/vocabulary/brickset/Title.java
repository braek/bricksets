package io.bricksets.vocabulary.brickset;

import io.bricksets.vocabulary.domain.ValueObject;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public final class Title implements ValueObject {

    private final String value;

    private Title(final String str) {
        final var sanitized = ofNullable(str)
                .map(String::trim)
                .orElse(null);
        if (isNull(sanitized) || sanitized.length() > 100) {
            throw new InvalidTitleException(str);
        }
        this.value = sanitized;
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
        Title title = (Title) o;
        return Objects.equals(getValue(), title.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
