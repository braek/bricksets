package io.bricksets.api;

import io.bricksets.vocabulary.brickset.BricksetId;

public interface CreateBricksetPresenter {

    void created(BricksetId bricksetId);

    void bricksetNumberAlreadyExists();
}