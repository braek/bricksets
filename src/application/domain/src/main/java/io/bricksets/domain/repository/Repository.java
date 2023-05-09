package io.bricksets.domain.repository;

import io.bricksets.domain.aggregate.Aggregate;
import io.bricksets.vocabulary.domain.AggregateId;

import java.util.Optional;

public interface Repository<AGGREGATE_ID extends AggregateId, AGGREGATE extends Aggregate> {

    Optional<AGGREGATE> get(AGGREGATE_ID aggregateId);

    void save(AGGREGATE aggregate);
}