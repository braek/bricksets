package be.koder.bricksets.swagger.brickset.endpoint;

import be.koder.bricksets.swagger.brickset.request.CreateBricksetRequest;
import be.koder.bricksets.swagger.brickset.response.BricksetCreatedResponse;
import be.koder.bricksets.swagger.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Emerald Night",
                                            value = """
                                                    {
                                                    	"number": "10194",
                                                    	"title": "Emerald Night"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Horizon Express",
                                            value = """
                                                    {
                                                    	"number": "10233",
                                                    	"title": "Horizon Express"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Crocodile Locomotive",
                                            value = """
                                                    {
                                                    	"number": "10277",
                                                    	"title": "Crocodile Locomotive"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
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
