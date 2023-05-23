package io.bricksets.facade;

import io.bricksets.vocabulary.brickset.BricksetId;

public interface RemoveBricksetPresenter {

    void removed(BricksetId bricksetId);

    void bricksetNotFound();
}