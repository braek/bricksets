package be.koder.bricksets.api.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetId;

public interface RemoveBricksetPresenter {

    void removed(BricksetId bricksetId);

    void bricksetNotFound();
}