package be.koder.bricksets.rdbms.repository;

import be.koder.bricksets.domain.brickset.Brickset;
import be.koder.bricksets.domain.brickset.BricksetNumberService;
import be.koder.bricksets.domain.brickset.BricksetRepository;
import be.koder.bricksets.domain.brickset.event.BricksetCreated;
import be.koder.bricksets.domain.brickset.event.BricksetRemoved;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
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