package io.bricksets.rdbms;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import io.bricksets.rdbms.mapper.EventMapper;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;


public class RdbmsBricksetRepository extends RdbmsBaseRepository implements BricksetRepository, BricksetNumberService {

    public RdbmsBricksetRepository(final DSLContext dsl) {
        super(dsl);
    }

    @Override
    public boolean exists(final BricksetNumber number) {
        final Map<BricksetId, BricksetNumber> numbers = new HashMap<>();
        var events = dsl.selectFrom(Tables.EVENT)
                .where(Tables.EVENT.EVENT_CLASS.eq(BricksetCreated.class.getSimpleName()))
                .or(Tables.EVENT.EVENT_CLASS.eq(BricksetRemoved.class.getSimpleName()))
                .orderBy(Tables.EVENT.POSITION.asc())
                .fetch()
                .stream()
                .map(it -> EventMapper.INSTANCE.map(it, emptyList()))
                .toList();
        events.forEach(event -> {
            if (event instanceof BricksetCreated created) {
                numbers.put(created.bricksetId(), created.number());
            }
            if (event instanceof BricksetRemoved removed) {
                numbers.remove(removed.bricksetId());
            }
        });
        return numbers.containsValue(number);
    }

    @Override
    public Optional<Brickset> get(final BricksetId bricksetId) {
        var eventStream = getEventStream(bricksetId);
        if (eventStream.isEmpty() || eventStream.containsEventOfType(BricksetRemoved.class)) {
            return Optional.empty();
        }
        return Optional.of(new Brickset(eventStream));
    }

    @Override
    @Transactional
    public void save(final Brickset brickset) {
        super.save(brickset);
    }
}