package io.bricksets.swagger.brickset;

import io.bricksets.facade.CreateBrickset;
import io.bricksets.swagger.brickset.endpoint.CreateBricksetEndpoint;
import io.bricksets.swagger.brickset.presenter.CreateBricksetRestPresenter;
import io.bricksets.swagger.brickset.request.CreateBricksetRequest;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BricksetController implements CreateBricksetEndpoint {

    private final CreateBrickset createBrickset;

    public BricksetController(CreateBrickset createBrickset) {
        this.createBrickset = createBrickset;
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
}