package be.koder.bricksets.swagger.brickset.endpoint;

import be.koder.bricksets.swagger.response.ErrorResponse;
import be.koder.bricksets.swagger.brickset.request.ModifyBricksetRequest;
import be.koder.bricksets.swagger.brickset.response.BricksetModifiedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface ModifyBricksetEndpoint {
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
                                            implementation = BricksetModifiedResponse.class
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
    @PatchMapping("/bricksets/{bricksetId}")
    ResponseEntity<Object> modifyBrickset(@PathVariable UUID bricksetId, @RequestBody ModifyBricksetRequest request);
}