package io.bricksets.rdbms.mapper;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import io.bricksets.vocabulary.brickset.BricksetId;

public final class BricksetIdDeserializer extends FromStringDeserializer<BricksetId> {

    BricksetIdDeserializer() {
        super(BricksetId.class);
    }

    @Override
    protected BricksetId _deserialize(final String value, final DeserializationContext context) {
        return BricksetId.fromString(value);
    }
}