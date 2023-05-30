package io.bricksets.rdbms.repository;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import org.jooq.DSLContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RdbmsBricksetRepository extends RdbmsBaseRepository implements BricksetRepository, BricksetNumberService {

    public RdbmsBricksetRepository(final DSLContext dsl) {
        super(dsl);
    }

    @Override
    public boolean exists(final BricksetNumber number) {
        var eventStream = openEventStream(
                BricksetCreated.class,
                BricksetRemoved.class
        );
        final Map<BricksetId, BricksetNumber> numbers = new HashMap<>();
        eventStream.events().forEach(event -> {
            if (event instanceof BricksetCreated concrete) {
                numbers.put(concrete.bricksetId(), concrete.number());
            }
            if (event instanceof BricksetRemoved concrete) {
                numbers.remove(concrete.bricksetId());
            }
        });
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
        super.save(brickset);
    }
}