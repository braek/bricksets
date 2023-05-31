package be.koder.bricksets.test;

import be.koder.bricksets.api.brickset.CreateBricksetPresenter;
import be.koder.bricksets.vocabulary.brickset.BricksetId;

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