package be.koder.bricksets.swagger.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ErrorResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String message) {
}