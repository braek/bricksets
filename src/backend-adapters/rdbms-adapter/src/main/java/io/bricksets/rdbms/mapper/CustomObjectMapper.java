package io.bricksets.rdbms.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.bricksets.domain.event.Event;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public final class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        super();
        final var bindings = new SimpleModule();

        // Serializers
        bindings.addSerializer(BricksetNumber.class, new ToStringSerializer());
        bindings.addSerializer(BricksetTitle.class, new ToStringSerializer());

        // Mixins
        bindings.setMixInAnnotation(Event.class, EventMixin.class);

        registerModule(bindings);
    }
}