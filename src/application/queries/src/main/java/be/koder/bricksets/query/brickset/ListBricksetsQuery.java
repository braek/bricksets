package be.koder.bricksets.query.brickset;

import be.koder.bricksets.api.brickset.ListBricksets;
import be.koder.bricksets.vocabulary.brickset.BricksetListItem;

import java.util.List;

public final class ListBricksetsQuery implements ListBricksets {

    private final BricksetView bricksetView;

    public ListBricksetsQuery(BricksetView bricksetView) {
        this.bricksetView = bricksetView;
    }

    @Override
    public List<BricksetListItem> listBricksets() {
        return bricksetView.listBricksets();
    }
}