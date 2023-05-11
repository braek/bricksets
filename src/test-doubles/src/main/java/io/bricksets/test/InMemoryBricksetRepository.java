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
        var eventStream = query(bricksetId);
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
        var eventStream = query(brickset.getId());
        if (eventStream.isEmpty()) {
            store(brickset.getMutatingEvents());
            return;
        }
        if (!Objects.equals(eventStream.getLastEventId(), brickset.getLastEventId())) {
            throw new EventStreamOptimisticLockException(brickset.getLastEventId());
        }
        store(brickset.getMutatingEvents());
    }

    private void store(List<Event> events) {
        events.forEach(event -> {
            if (event instanceof BricksetCreated created) {
                numbers.add(created.number());
            }
            if (event instanceof BricksetRemoved removed) {
                numbers.remove(removed.number());
            }
        });
        eventStore.addAll(events);
    }

    private EventStream query(final BricksetId bricksetId) {
        return new EventStream(eventStore.stream()
                .filter(it -> it.tags().contains(bricksetId))
                .toList());
    }
}