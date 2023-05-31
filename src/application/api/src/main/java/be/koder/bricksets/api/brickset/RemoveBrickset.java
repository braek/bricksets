package be.koder.bricksets.api.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetId;

public interface RemoveBrickset {
    void removeBrickset(BricksetId bricksetId, RemoveBricksetPresenter presenter);
}