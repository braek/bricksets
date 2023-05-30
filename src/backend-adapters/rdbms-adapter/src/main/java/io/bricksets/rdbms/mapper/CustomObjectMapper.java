package io.bricksets.rdbms.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;

public final class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        super();
        final var bindings = new SimpleModule();

        // Serializers
        bindings.addSerializer(BricksetId.class, new ToStringSerializer());
        bindings.addSerializer(BricksetNumber.class, new ToStringSerializer());
        bindings.addSerializer(BricksetTitle.class, new ToStringSerializer());
        bindings.addSerializer(EventId.class, new ToStringSerializer());
        bindings.addSerializer(Timestamp.class, new ToStringSerializer());

        // Mixins
        bindings.setMixInAnnotation(Event.class, EventMixin.class);

        registerModule(bindings);
    }
}