package io.bricksets.query.brickset;

import io.bricksets.api.brickset.ListBricksets;
import io.bricksets.vocabulary.brickset.BricksetListItem;

import java.util.List;

public final class ListBricksetsQuery implements ListBricksets {

    private final BricksetView bricksetView;

    public ListBricksetsQuery(final BricksetView bricksetView) {
        this.bricksetView = bricksetView;
    }

    @Override
    public List<BricksetListItem> listBricksets() {
        return bricksetView.listBricksets();
    }
}