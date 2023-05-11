package io.bricksets.domain.aggregate;

import io.bricksets.vocabulary.domain.AggregateId;

// Marker interface for aggregates in the domain
public interface Aggregate {
    AggregateId getId();
}