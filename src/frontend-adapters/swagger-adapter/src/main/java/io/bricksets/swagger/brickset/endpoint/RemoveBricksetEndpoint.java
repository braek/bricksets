package io.bricksets.swagger.brickset.endpoint;

import io.bricksets.swagger.brickset.response.BricksetRemovedResponse;
import io.bricksets.swagger.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface RemoveBricksetEndpoint {
    @Operation(
            tags = {
                    "Bricksets"
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BricksetRemovedResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/bricksets/{bricksetId}")
    ResponseEntity<Object> removeBrickset(@PathVariable UUID bricksetId);
}
