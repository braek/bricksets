package io.bricksets.rdbms.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

public abstract class EventMixin {

    @JsonIgnore
    abstract EventId id();

    @JsonIgnore
    abstract Timestamp occurredOn();
}