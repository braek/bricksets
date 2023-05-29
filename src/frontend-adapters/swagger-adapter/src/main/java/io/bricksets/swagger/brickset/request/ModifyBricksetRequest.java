package io.bricksets.swagger.brickset.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ModifyBricksetRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title
) {
}