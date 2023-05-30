package io.bricksets.query.brickset;

import io.bricksets.vocabulary.brickset.BricksetListItem;

import java.util.List;

public interface BricksetView {
    List<BricksetListItem> listBricksets();
}