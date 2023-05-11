package io.bricksets.domain.event;

import java.util.List;

public record EventStream(List<Event> events) {
}