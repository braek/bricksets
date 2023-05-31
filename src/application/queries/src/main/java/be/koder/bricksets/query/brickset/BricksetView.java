package be.koder.bricksets.query.brickset;

import be.koder.bricksets.vocabulary.brickset.BricksetListItem;

import java.util.List;

public interface BricksetView {
    List<BricksetListItem> listBricksets();
}