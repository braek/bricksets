package io.bricksets.swagger.brickset.presenter;

import io.bricksets.api.brickset.ModifyBricksetPresenter;
import io.bricksets.swagger.brickset.response.BricksetModifiedResponse;
import io.bricksets.swagger.presenter.RestPresenter;
import io.bricksets.swagger.response.ErrorResponse;
import io.bricksets.vocabulary.brickset.BricksetId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ModifyBricksetRestPresenter implements RestPresenter, ModifyBricksetPresenter {

    private ResponseEntity<Object> response;

    @Override
    public void modified(BricksetId bricksetId) {
        response = ResponseEntity.status(HttpStatus.OK).body(new BricksetModifiedResponse(bricksetId.getValue()));
    }

    @Override
    public void bricksetNotFound() {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("This Brickset was not found."));
    }

    @Override
    public ResponseEntity<Object> getResponse() {
        return response;
    }
}