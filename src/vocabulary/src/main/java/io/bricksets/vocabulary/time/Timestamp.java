package io.bricksets.vocabulary.time;

import io.bricksets.vocabulary.domain.ValueObject;

import java.time.Instant;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class Timestamp implements ValueObject {

    private final Instant value;

    private Timestamp(Instant value) {
        this.value = requireNonNull(value);
    }

    public static Timestamp fromInstant(Instant instant) {
        return new Timestamp(instant);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp timestamp = (Timestamp) o;
        return Objects.equals(getValue(), timestamp.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public Instant getValue() {
        return value;
    }
}
