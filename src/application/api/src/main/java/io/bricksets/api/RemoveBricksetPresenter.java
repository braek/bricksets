package io.bricksets.api;

import io.bricksets.vocabulary.brickset.BricksetId;

public interface RemoveBricksetPresenter {

    void removed(BricksetId bricksetId);

    void bricksetNotFound();
}