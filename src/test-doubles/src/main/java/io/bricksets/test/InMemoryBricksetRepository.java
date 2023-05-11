package io.bricksets.test;

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
                .filter(it -> it instanceof BricksetCreated)
                .map(it -> ((BricksetCreated) it).number())
                .collect(Collectors.toSet());
        var removedNumbers = eventStore.stream()
                .filter(it -> it instanceof BricksetRemoved)
                .map(it -> ((BricksetRemoved) it).number())
                .collect(Collectors.toSet());
        numbers.removeAll(removedNumbers);
        return numbers.contains(number);
    }

    @Override
    public Optional<Brickset> get(final BricksetId bricksetId) {
        var eventStream = getEventStreamForBrickset(bricksetId);
        if (eventStream.isEmpty() || eventStream.containsInstanceOf(BricksetRemoved.class)) {
            return Optional.empty();
        }
        return Optional.of(new Brickset(eventStream));
    }

    @Override
    public void save(final Brickset brickset) {
        if (brickset.getMutatingEvents().isEmpty()) {
            return;
        }
        var eventStream = getEventStreamForBrickset(brickset.getId());
        if (eventStream.isEmpty()) {
            eventStore.addAll(eventStream.events());
            return;
        }
        if (!Objects.equals(eventStream.getLastEventId(), brickset.getLastEventId())) {
            throw new EventStreamOptimisticLockException(brickset.getLastEventId());
        }
        eventStore.addAll(eventStream.events());
    }

    private EventStream getEventStreamForBrickset(final BricksetId bricksetId) {
        return new EventStream(eventStore.stream()
                .filter(it -> it.tags().contains(bricksetId))
                .toList());
    }
}