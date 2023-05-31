package be.koder.bricksets.domain.repository;

import be.koder.bricksets.domain.aggregate.Aggregate;
import be.koder.bricksets.vocabulary.domain.AggregateId;

import java.util.Optional;

public interface Repository<AGGREGATE_ID extends AggregateId, AGGREGATE extends Aggregate> {

    Optional<AGGREGATE> get(AGGREGATE_ID aggregateId);

    void save(AGGREGATE aggregate);
}