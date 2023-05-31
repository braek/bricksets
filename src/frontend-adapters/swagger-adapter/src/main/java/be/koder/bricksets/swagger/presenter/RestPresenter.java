package be.koder.bricksets.swagger.presenter;

import org.springframework.http.ResponseEntity;

public interface RestPresenter {
    ResponseEntity<Object> getResponse();
}