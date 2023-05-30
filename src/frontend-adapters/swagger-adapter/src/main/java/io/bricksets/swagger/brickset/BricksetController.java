package io.bricksets.swagger.brickset;

import io.bricksets.api.brickset.CreateBrickset;
import io.bricksets.api.brickset.ListBricksets;
import io.bricksets.api.brickset.ModifyBrickset;
import io.bricksets.api.brickset.RemoveBrickset;
import io.bricksets.swagger.brickset.endpoint.CreateBricksetEndpoint;
import io.bricksets.swagger.brickset.endpoint.ListBricksetsEndpoint;
import io.bricksets.swagger.brickset.endpoint.ModifyBricksetEndpoint;
import io.bricksets.swagger.brickset.endpoint.RemoveBricksetEndpoint;
import io.bricksets.swagger.brickset.presenter.CreateBricksetRestPresenter;
import io.bricksets.swagger.brickset.presenter.ModifyBricksetRestPresenter;
import io.bricksets.swagger.brickset.presenter.RemoveBricksetRestPresenter;
import io.bricksets.swagger.brickset.request.CreateBricksetRequest;
import io.bricksets.swagger.brickset.request.ModifyBricksetRequest;
import io.bricksets.swagger.brickset.response.BricksetListItem;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class BricksetController implements ListBricksetsEndpoint, CreateBricksetEndpoint, ModifyBricksetEndpoint, RemoveBricksetEndpoint {

    private final CreateBrickset createBrickset;
    private final ModifyBrickset modifyBrickset;
    private final RemoveBrickset removeBrickset;
    private final ListBricksets listBricksets;

    public BricksetController(CreateBrickset createBrickset, ModifyBrickset modifyBrickset, RemoveBrickset removeBrickset, ListBricksets listBricksets) {
        this.createBrickset = createBrickset;
        this.modifyBrickset = modifyBrickset;
        this.removeBrickset = removeBrickset;
        this.listBricksets = listBricksets;
    }

    @Override
    public ResponseEntity<Object> createBrickset(CreateBricksetRequest request) {
        var presenter = new CreateBricksetRestPresenter();
        createBrickset.createBrickset(
                BricksetNumber.fromString(request.number()),
                BricksetTitle.fromString(request.title()),
                presenter
        );
        return presenter.getResponse();
    }

    @Override
    public ResponseEntity<Object> modifyBrickset(UUID bricksetId, ModifyBricksetRequest request) {
        var presenter = new ModifyBricksetRestPresenter();
        modifyBrickset.modifyBrickset(
                BricksetId.fromUuid(bricksetId),
                BricksetTitle.fromString(request.title()),
                presenter
        );
        return presenter.getResponse();
    }

    @Override
    public ResponseEntity<Object> removeBrickset(UUID bricksetId) {
        var presenter = new RemoveBricksetRestPresenter();
        removeBrickset.removeBrickset(
                BricksetId.fromUuid(bricksetId),
                presenter
        );
        return presenter.getResponse();
    }

    @Override
    public ResponseEntity<List<BricksetListItem>> listBricksets() {
        return ResponseEntity.ok(listBricksets.listBricksets()
                .stream()
                .map(it -> new BricksetListItem(
                        it.id().getValue(),
                        it.number().toString(),
                        it.title().toString()
                ))
                .toList());
    }
}