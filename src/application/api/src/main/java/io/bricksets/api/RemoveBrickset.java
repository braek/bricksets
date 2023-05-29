package io.bricksets.api;

import io.bricksets.vocabulary.brickset.BricksetId;

public interface RemoveBrickset {
    void removeBrickset(BricksetId bricksetId, RemoveBricksetPresenter presenter);
}