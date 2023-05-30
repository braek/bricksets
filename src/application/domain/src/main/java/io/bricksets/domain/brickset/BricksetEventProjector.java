package io.bricksets.domain.brickset;

import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetModified;
import io.bricksets.domain.brickset.event.BricksetRemoved;

public interface BricksetEventProjector {

    void project(BricksetCreated bricksetCreated);

    void project(BricksetModified bricksetModified);

    void project(BricksetRemoved bricksetRemoved);
}