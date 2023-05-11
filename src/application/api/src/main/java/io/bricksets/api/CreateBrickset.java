package io.bricksets.api;

import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public interface CreateBrickset {
    void createBrickset(BricksetNumber number, BricksetTitle title, CreateBricksetPresenter presenter);
}