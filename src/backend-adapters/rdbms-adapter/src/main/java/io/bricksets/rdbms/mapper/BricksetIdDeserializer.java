package io.bricksets.rdbms.mapper;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import io.bricksets.vocabulary.brickset.BricksetId;

import java.util.UUID;

public final class BricksetIdDeserializer extends FromStringDeserializer<BricksetId> {

    BricksetIdDeserializer() {
        super(BricksetId.class);
    }

    @Override
    protected BricksetId _deserialize(String value, DeserializationContext context) {
        return BricksetId.fromUuid(UUID.fromString(value));
    }
}