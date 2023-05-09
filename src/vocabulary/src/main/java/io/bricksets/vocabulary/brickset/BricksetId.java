package io.bricksets.vocabulary.brickset;

import io.bricksets.vocabulary.domain.AggregateId;

import java.util.Objects;
import java.util.UUID;

public final class BricksetId implements AggregateId {

    private final UUID value;

    private BricksetId(final UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BricksetId that = (BricksetId) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
