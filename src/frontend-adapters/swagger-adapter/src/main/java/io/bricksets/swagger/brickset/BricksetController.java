package io.bricksets.swagger.brickset;

import io.bricksets.facade.CreateBrickset;
import io.bricksets.facade.ModifyBrickset;
import io.bricksets.facade.RemoveBrickset;
import io.bricksets.swagger.brickset.endpoint.CreateBricksetEndpoint;
import io.bricksets.swagger.brickset.endpoint.ModifyBricksetEndpoint;
import io.bricksets.swagger.brickset.endpoint.RemoveBricksetEndpoint;
import io.bricksets.swagger.brickset.presenter.CreateBricksetRestPresenter;
import io.bricksets.swagger.brickset.presenter.ModifyBricksetRestPresenter;
import io.bricksets.swagger.brickset.presenter.RemoveBricksetRestPresenter;
import io.bricksets.swagger.brickset.request.CreateBricksetRequest;
import io.bricksets.swagger.brickset.request.ModifyBricksetRequest;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BricksetController implements CreateBricksetEndpoint, ModifyBricksetEndpoint, RemoveBricksetEndpoint {

    private final CreateBrickset createBrickset;
    private final ModifyBrickset modifyBrickset;
    private final RemoveBrickset removeBrickset;

    public BricksetController(CreateBrickset createBrickset, ModifyBrickset modifyBrickset, RemoveBrickset removeBrickset) {
        this.createBrickset = createBrickset;
        this.modifyBrickset = modifyBrickset;
        this.removeBrickset = removeBrickset;
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
}