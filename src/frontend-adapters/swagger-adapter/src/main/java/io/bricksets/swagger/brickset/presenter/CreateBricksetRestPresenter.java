package io.bricksets.swagger.brickset.presenter;

import io.bricksets.api.CreateBricksetPresenter;
import io.bricksets.swagger.brickset.response.BricksetCreatedResponse;
import io.bricksets.swagger.presenter.RestPresenter;
import io.bricksets.swagger.response.ErrorResponse;
import io.bricksets.vocabulary.brickset.BricksetId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class CreateBricksetRestPresenter implements RestPresenter, CreateBricksetPresenter {

    private ResponseEntity<Object> response;

    @Override
    public void created(BricksetId bricksetId) {
        response = ResponseEntity.status(HttpStatus.CREATED).body(new BricksetCreatedResponse(bricksetId.getValue()));
    }

    @Override
    public void bricksetNumberAlreadyExists() {
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("This BricksetNumber already exists."));
    }

    @Override
    public ResponseEntity<Object> getResponse() {
        return response;
    }
}