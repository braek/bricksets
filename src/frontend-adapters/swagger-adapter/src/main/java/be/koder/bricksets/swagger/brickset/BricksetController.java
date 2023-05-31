package be.koder.bricksets.swagger.brickset;

import be.koder.bricksets.api.brickset.CreateBrickset;
import be.koder.bricksets.api.brickset.ListBricksets;
import be.koder.bricksets.api.brickset.ModifyBrickset;
import be.koder.bricksets.api.brickset.RemoveBrickset;
import be.koder.bricksets.swagger.brickset.endpoint.CreateBricksetEndpoint;
import be.koder.bricksets.swagger.brickset.endpoint.ListBricksetsEndpoint;
import be.koder.bricksets.swagger.brickset.endpoint.ModifyBricksetEndpoint;
import be.koder.bricksets.swagger.brickset.presenter.CreateBricksetRestPresenter;
import be.koder.bricksets.swagger.brickset.presenter.RemoveBricksetRestPresenter;
import be.koder.bricksets.swagger.brickset.request.CreateBricksetRequest;
import be.koder.bricksets.swagger.brickset.response.BricksetListItem;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.swagger.brickset.endpoint.RemoveBricksetEndpoint;
import be.koder.bricksets.swagger.brickset.presenter.ModifyBricksetRestPresenter;
import be.koder.bricksets.swagger.brickset.request.ModifyBricksetRequest;
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
        // TODO: validate translation from JSON types to vocabulary types
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
        // TODO: validate translation from JSON types to vocabulary types
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
        // TODO: validate translation from JSON types to vocabulary types
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
                        it.title().toString(),
                        it.createdOn().getValue(),
                        it.modifiedOn().getValue()
                ))
                .toList());
    }
}