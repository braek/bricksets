package be.koder.bricksets.domain.aggregate;

import be.koder.bricksets.vocabulary.domain.AggregateId;

// Marker interface for aggregates in the domain
public interface Aggregate {
    AggregateId getId();
}