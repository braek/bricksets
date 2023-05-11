package io.bricksets.test;

import io.bricksets.domain.brickset.*;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockException;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;

import java.util.*;

public final class InMemoryBricksetRepository implements BricksetRepository, BricksetNumberService {

    private final List<Event> eventStore = new ArrayList<>();
    private final Set<BricksetNumber> numbers = new HashSet<>();

    @Override
    public boolean exists(final BricksetNumber number) {
        return numbers.contains(number);
    }

    @Override
    public Optional<Brickset> get(final BricksetId bricksetId) {
        var eventStream = getBricksetEventStream(bricksetId);
        if (eventStream.isEmpty() || eventStream.containsInstanceOf(BricksetRemoved.class)) {
            return Optional.empty();
        }
        return Optional.of(new Brickset(eventStream));
    }

    @Override
    public void save(final Brickset brickset) {
        var eventStream = getBricksetEventStream(brickset.getId());
        if (eventStream.isEmpty()) {
            return;
        }
        if (!Objects.equals(eventStream.getLastEventId(), brickset.getLastEventId())) {
            throw new EventStreamOptimisticLockException();
        }
        brickset.getMutatingEvents().forEach(event -> {
            if (event instanceof BricksetCreated created) {
                numbers.add(created.number());
            }
            if (event instanceof BricksetRemoved) {
                numbers.remove(brickset.getNumber());
            }
        });
        eventStore.addAll(brickset.getMutatingEvents());
    }

    private EventStream getBricksetEventStream(final BricksetId bricksetId) {
        return new EventStream(eventStore.stream()
                .filter(it -> it.tags().contains(bricksetId))
                .toList());
    }
}