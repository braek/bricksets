package be.koder.bricksets.api.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;

public interface ModifyBrickset {
    void modifyBrickset(BricksetId bricksetId, BricksetTitle title, ModifyBricksetPresenter presenter);
}