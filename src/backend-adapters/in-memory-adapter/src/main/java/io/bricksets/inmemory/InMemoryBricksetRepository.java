package io.bricksets.inmemory;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import io.bricksets.domain.event.Event;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.event.EventStreamOptimisticLockingException;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;

import java.util.*;
import java.util.stream.Collectors;

public final class InMemoryBricksetRepository implements BricksetRepository, BricksetNumberService {

    private final List<Event> events = new ArrayList<>();

    @Override
    public boolean exists(final BricksetNumber number) {
        var numbers = new HashMap<BricksetId, BricksetNumber>();
        events.stream()
                .filter(BricksetCreated.class::isInstance)
                .map(BricksetCreated.class::cast)
                .map(it -> Map.entry(it.bricksetId(), it.number()))
                .forEach(it -> numbers.put(it.getKey(), it.getValue()));
        events.stream()
                .filter(BricksetRemoved.class::isInstance)
                .map(BricksetRemoved.class::cast)
                .map(BricksetRemoved::bricksetId)
                .collect(Collectors.toSet())
                .forEach(numbers::remove);
        return numbers.containsValue(number);
    }

    @Override
    public Optional<Brickset> get(final BricksetId bricksetId) {
        var eventStream = openEventStream(bricksetId);
        if (eventStream.isEmpty() || eventStream.containsEventOfType(BricksetRemoved.class)) {
            return Optional.empty();
        }
        return Optional.of(new Brickset(eventStream));
    }

    @Override
    public void save(final Brickset brickset) {

        // Nothing changed in the aggregate
        if (brickset.hasNoMutations()) {
            return;
        }

        var persisted = openEventStream(brickset.getId());
        if (brickset.isNotEqualTo(persisted)) {
            throw new EventStreamOptimisticLockingException(brickset.getLastEventId());
        }

        events.addAll(brickset.getMutations().events());
    }

    private EventStream openEventStream(final BricksetId bricksetId) {
        return new EventStream(events.stream()
                .filter(it -> it.tags().contains(bricksetId))
                .toList());
    }
}