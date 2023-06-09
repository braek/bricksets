package be.koder.bricksets.vocabulary.brickset;

import be.koder.bricksets.vocabulary.domain.AggregateId;

import java.util.Objects;
import java.util.UUID;

public final class BricksetId implements AggregateId {

    private final UUID value;

    private BricksetId(final UUID value) {
        this.value = value;
    }

    public static BricksetId createNew() {
        return new BricksetId(UUID.randomUUID());
    }

    public static BricksetId fromUuid(final UUID uuid) {
        return new BricksetId(uuid);
    }

    public static BricksetId fromString(final String str) {
        return new BricksetId(UUID.fromString(str));
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
