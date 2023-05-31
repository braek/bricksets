package be.koder.bricksets.swagger.brickset.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record BricksetModifiedResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) UUID bricksetId) {
}