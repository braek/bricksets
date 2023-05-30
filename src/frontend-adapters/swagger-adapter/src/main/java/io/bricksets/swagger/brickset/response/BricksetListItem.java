package io.bricksets.swagger.brickset.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record BricksetListItem(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) UUID id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String number,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title
) {
}