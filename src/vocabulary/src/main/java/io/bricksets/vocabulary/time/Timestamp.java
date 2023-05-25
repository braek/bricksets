package io.bricksets.vocabulary.time;

import io.bricksets.vocabulary.domain.ValueObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class Timestamp implements ValueObject {

    private final Instant value;

    private Timestamp(final Instant value) {
        this.value = requireNonNull(value);
    }

    public static Timestamp fromInstant(final Instant instant) {
        return new Timestamp(instant);
    }

    public static Timestamp fromString(final String str) {
        return new Timestamp(Instant.parse(str));
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofInstant(value, ZoneOffset.UTC);
    }

    public static Timestamp fromLocalDateTime(final LocalDateTime localDateTime) {
        return new Timestamp(localDateTime.toInstant(ZoneOffset.UTC));
    }

    @Override
    public Instant getValue() {
        return value;
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
}