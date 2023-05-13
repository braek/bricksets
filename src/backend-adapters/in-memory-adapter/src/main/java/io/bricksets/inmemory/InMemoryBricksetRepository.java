package io.bricksets.inmemory;

import io.bricksets.domain.brickset.*;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockException;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class InMemoryBricksetRepository implements BricksetRepository, BricksetNumberService {

    private final List<Event> eventStore = new ArrayList<>();

    @Override
    public boolean exists(final BricksetNumber number) {
        var numbers = eventStore.stream()
                .filter(BricksetCreated.class::isInstance)
                .map(BricksetCreated.class::cast)
                .map(BricksetCreated::number)
                .collect(Collectors.toSet());
        var removed = eventStore.stream()
                .filter(BricksetRemoved.class::isInstance)
                .map(BricksetRemoved.class::cast)
                .map(BricksetRemoved::number)
                .collect(Collectors.toSet());
        numbers.removeAll(removed);
        return numbers.contains(number);
    }

    @Override
    public Optional<Brickset> get(final BricksetId bricksetId) {
        var eventStream = query(bricksetId);
        if (eventStream.isEmpty() || eventStream.containsEventOfType(BricksetRemoved.class)) {
            return Optional.empty();
        }
        return Optional.of(new Brickset(eventStream));
    }

    @Override
    public void save(final Brickset brickset) {

        // Nothing changed in the aggregate
        if (brickset.getMutatingEvents().isEmpty()) {
            return;
        }

        // The aggregate was a new instance
        var eventStream = query(brickset.getId());
        if (eventStream.isEmpty()) {
            eventStore.addAll(brickset.getMutatingEvents());
            return;
        }

        // Update the aggregate, if optimistic locking does not fail
        if (!Objects.equals(eventStream.getLastEventId(), brickset.getLastEventId())) {
            throw new EventStreamOptimisticLockException(brickset.getLastEventId());
        }
        eventStore.addAll(brickset.getMutatingEvents());
    }

    private EventStream query(final BricksetId bricksetId) {
        return new EventStream(eventStore.stream()
                .filter(it -> it.tags().contains(bricksetId))
                .toList());
    }
}