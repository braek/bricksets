package io.bricksets.facade;

import io.bricksets.vocabulary.brickset.BricksetId;

public interface CreateBricksetPresenter {

    void created(BricksetId bricksetId);

    void bricksetNumberAlreadyExists();
}