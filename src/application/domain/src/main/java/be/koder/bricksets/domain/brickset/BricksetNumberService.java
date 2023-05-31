package be.koder.bricksets.domain.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetNumber;

public interface BricksetNumberService {
    boolean exists(BricksetNumber number);
}