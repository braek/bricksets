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

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;


public class RdbmsBricksetRepository extends RdbmsBaseRepository implements BricksetRepository, BricksetNumberService {

    public RdbmsBricksetRepository(final DSLContext dsl) {
        super(dsl);
    }

    @Override
    public boolean exists(final BricksetNumber number) {
        var creations = dsl.selectFrom(Tables.EVENT)
                .where(Tables.EVENT.EVENT_CLASS.eq(BricksetCreated.class.getSimpleName()))
                .fetch()
                .stream()
                .map(it -> EventMapper.INSTANCE.map(it, emptyList()))
                .map(BricksetCreated.class::cast)
                .map(BricksetCreated::number)
                .collect(Collectors.toSet());
        var removals = dsl.selectFrom(Tables.EVENT)
                .where(Tables.EVENT.EVENT_CLASS.eq(BricksetRemoved.class.getSimpleName()))
                .fetch()
                .stream()
                .map(it -> EventMapper.INSTANCE.map(it, emptyList()))
                .map(BricksetRemoved.class::cast)
                .map(BricksetRemoved::number)
                .collect(Collectors.toSet());
        creations.removeAll(removals);
        return creations.contains(number);
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