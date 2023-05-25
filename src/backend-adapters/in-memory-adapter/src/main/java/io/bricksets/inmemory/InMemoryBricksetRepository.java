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

    private final List<Event> eventStore = new ArrayList<>();

    @Override
    public boolean exists(final BricksetNumber number) {
        var numbers = new HashMap<BricksetId, BricksetNumber>();
        eventStore.stream()
                .filter(BricksetCreated.class::isInstance)
                .map(BricksetCreated.class::cast)
                .map(it -> Map.entry(it.bricksetId(), it.number()))
                .forEach(it -> numbers.put(it.getKey(), it.getValue()));
        eventStore.stream()
                .filter(BricksetRemoved.class::isInstance)
                .map(BricksetRemoved.class::cast)
                .map(BricksetRemoved::bricksetId)
                .collect(Collectors.toSet())
                .forEach(numbers::remove);
        return numbers.containsValue(number);
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
        if (brickset.getMutations().isEmpty()) {
            return;
        }

        // The aggregate was a new instance
        var eventStream = query(brickset.getId());
        if (eventStream.isEmpty()) {
            eventStore.addAll(brickset.getMutations().events());
            return;
        }

        // Update the aggregate, if optimistic locking does not fail
        if (!Objects.equals(eventStream.getPointer(), brickset.getStatusQuoPointer())) {
            throw new EventStreamOptimisticLockingException(brickset.getStatusQuoPointer());
        }
        eventStore.addAll(brickset.getMutations().events());
    }

    private EventStream query(final BricksetId bricksetId) {
        return new EventStream(eventStore.stream()
                .filter(it -> it.tags().contains(bricksetId))
                .toList());
    }
}