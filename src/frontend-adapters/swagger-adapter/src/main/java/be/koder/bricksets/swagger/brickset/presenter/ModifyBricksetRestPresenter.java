package be.koder.bricksets.swagger.brickset.presenter;

import be.koder.bricksets.api.brickset.ModifyBricksetPresenter;
import be.koder.bricksets.swagger.brickset.response.BricksetModifiedResponse;
import be.koder.bricksets.swagger.presenter.RestPresenter;
import be.koder.bricksets.swagger.response.ErrorResponse;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
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