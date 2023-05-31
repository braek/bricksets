package be.koder.bricksets.swagger.brickset.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record CreateBricksetRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String number,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title
) {
}