package io.bricksets.query.brickset;

import io.bricksets.api.brickset.ListBricksets;
import io.bricksets.vocabulary.brickset.BricksetListItem;

import java.util.List;

public final class ListBricksetsQuery implements ListBricksets {

    private final BricksetView viewModel;

    public ListBricksetsQuery(BricksetView viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public List<BricksetListItem> listBricksets() {
        return viewModel.listBricksets();
    }
}