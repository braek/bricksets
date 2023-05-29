package io.bricksets.swagger.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ErrorResponse(String message) {
}