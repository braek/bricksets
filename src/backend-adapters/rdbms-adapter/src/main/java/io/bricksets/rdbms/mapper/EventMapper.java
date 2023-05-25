package io.bricksets.rdbms.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetModified;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import io.bricksets.domain.event.Event;
import org.jooq.JSONB;

import java.util.HashMap;
import java.util.Map;

public enum EventMapper {

    INSTANCE;

    private final ObjectMapper objectMapper = new CustomObjectMapper();

    public JSONB serialize(final Event event) {
        try {
            return JSONB.valueOf(objectMapper.writeValueAsString(event));
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Event deserialize(final JSONB json, final String clazz) {
        try {
            return objectMapper.readValue(json.data(), mapEventType(clazz));
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<? extends Event> mapEventType(final String clazz) {
        final Map<String, Class<? extends Event>> eventTypes = new HashMap<>();
        eventTypes.put(BricksetCreated.class.getSimpleName(), BricksetCreated.class);
        eventTypes.put(BricksetModified.class.getSimpleName(), BricksetModified.class);
        eventTypes.put(BricksetRemoved.class.getSimpleName(), BricksetRemoved.class);
        if (eventTypes.containsKey(clazz)) {
            return eventTypes.get(clazz);
        }
        throw new RuntimeException(String.format("Cannot convert input string (%s) to Event class", clazz));
    }
}