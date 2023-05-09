package io.bricksets.domain.brickset;

import io.bricksets.domain.aggregate.EventSourcedAggregate;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.Title;

public final class Brickset extends EventSourcedAggregate {

    private BricksetId id;
    private Title title;
}