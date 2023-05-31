package be.koder.bricksets.swagger.brickset.presenter;

import be.koder.bricksets.api.brickset.CreateBricksetPresenter;
import be.koder.bricksets.swagger.brickset.response.BricksetCreatedResponse;
import be.koder.bricksets.swagger.response.ErrorResponse;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.swagger.presenter.RestPresenter;
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