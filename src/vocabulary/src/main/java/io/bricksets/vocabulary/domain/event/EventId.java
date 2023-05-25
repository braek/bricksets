package io.bricksets.vocabulary.domain.event;

import io.bricksets.vocabulary.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class EventId implements ValueObject {

    private final UUID value;

    private EventId(final UUID value) {
        this.value = value;
    }

    public static EventId createNew() {
        return new EventId(UUID.randomUUID());
    }

    public static EventId fromString(final String str) {
        return new EventId(UUID.fromString(str));
    }

    public static EventId fromUuid(UUID uuid) {
        return new EventId(uuid);
    }

    @Override
    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventId eventId = (EventId) o;
        return Objects.equals(getValue(), eventId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}