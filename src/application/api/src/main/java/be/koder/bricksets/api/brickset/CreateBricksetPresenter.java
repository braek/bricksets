package be.koder.bricksets.api.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetId;

public interface CreateBricksetPresenter {

    void created(BricksetId bricksetId);

    void bricksetNumberAlreadyExists();
}