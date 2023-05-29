package io.bricksets.swagger.brickset.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record CreateBricksetRequest(String number, String title) {
}