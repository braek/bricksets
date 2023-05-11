package io.bricksets.test;

import io.bricksets.domain.event.EventStreamOptimisticLockException;
import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRemoved;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryBricksetRepository implements BricksetRepository, BricksetNumberService {

    private final List<Event> eventStore = new ArrayList<>();

    @Override
    public boolean exists(final BricksetNumber number) {
        return false;
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
        eventStore.addAll(brickset.getMutatingEvents());
    }

    private EventStream getBricksetEventStream(final BricksetId bricksetId) {
        return new EventStream(eventStore.stream()
                .filter(it -> it.tags().contains(bricksetId))
                .toList());
    }
}