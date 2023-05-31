package be.koder.bricksets.swagger.brickset.endpoint;

import be.koder.bricksets.swagger.brickset.response.BricksetListItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ListBricksetsEndpoint {
    @Operation(
            tags = {
                    "Bricksets"
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = BricksetListItem.class
                                            )
                                    )
                            )
                    )
            }
    )
    @GetMapping("/bricksets")
    ResponseEntity<List<BricksetListItem>> listBricksets();
}