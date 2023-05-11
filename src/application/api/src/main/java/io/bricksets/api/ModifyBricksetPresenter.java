package io.bricksets.api;

import io.bricksets.vocabulary.brickset.BricksetId;

public interface ModifyBricksetPresenter {

    void modified(BricksetId bricksetId);

    void bricksetNotFound();
}