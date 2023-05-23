package io.bricksets.test;

import io.bricksets.facade.CreateBricksetPresenter;
import io.bricksets.vocabulary.brickset.BricksetId;

public final class MockCreateBricksetPresenter implements CreateBricksetPresenter {

    @Override
    public void created(BricksetId bricksetId) {
        // Do nothing
    }

    @Override
    public void bricksetNumberAlreadyExists() {
        // Do nothing
    }
}