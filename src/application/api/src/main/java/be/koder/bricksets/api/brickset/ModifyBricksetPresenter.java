package be.koder.bricksets.api.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetId;

public interface ModifyBricksetPresenter {

    void modified(BricksetId bricksetId);

    void bricksetNotFound();
}