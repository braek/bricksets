package io.bricksets.swagger.brickset.endpoint;

import io.bricksets.swagger.brickset.request.CreateBricksetRequest;
import io.bricksets.swagger.brickset.response.BricksetCreatedResponse;
import io.bricksets.swagger.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CreateBricksetEndpoint {
    @Operation(
            tags = {
                    "Bricksets"
            },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BricksetCreatedResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/bricksets")
    ResponseEntity<Object> createBrickset(@RequestBody CreateBricksetRequest request);
}
