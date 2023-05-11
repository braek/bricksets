package io.bricksets.api;

import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public interface ModifyBrickset {
    void modifyBrickset(BricksetId bricksetId, BricksetTitle title, ModifyBricksetPresenter presenter);
}