package be.koder.bricksets.api.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;

public interface CreateBrickset {
    void createBrickset(BricksetNumber number, BricksetTitle title, CreateBricksetPresenter presenter);
}