package io.bricksets.facade;

import io.bricksets.vocabulary.brickset.BricksetId;

public interface RemoveBrickset {
    void removeBrickset(BricksetId bricksetId, RemoveBricksetPresenter presenter);
}