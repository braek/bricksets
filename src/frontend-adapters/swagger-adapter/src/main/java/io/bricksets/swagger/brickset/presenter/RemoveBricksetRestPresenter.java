package io.bricksets.swagger.brickset.presenter;

import io.bricksets.facade.RemoveBricksetPresenter;
import io.bricksets.swagger.brickset.response.BricksetRemovedResponse;
import io.bricksets.swagger.presenter.RestPresenter;
import io.bricksets.swagger.response.ErrorResponse;
import io.bricksets.vocabulary.brickset.BricksetId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class RemoveBricksetRestPresenter implements RestPresenter, RemoveBricksetPresenter {

    private ResponseEntity<Object> response;

    @Override
    public void removed(BricksetId bricksetId) {
        response = ResponseEntity.status(HttpStatus.OK).body(new BricksetRemovedResponse(bricksetId.getValue()));
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
