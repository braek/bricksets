package io.bricksets.domain.brickset;

import io.bricksets.vocabulary.brickset.BricksetNumber;

public interface BricksetNumberService {
    boolean exists(BricksetNumber number);
}