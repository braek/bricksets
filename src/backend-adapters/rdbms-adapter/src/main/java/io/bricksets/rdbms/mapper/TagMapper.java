package io.bricksets.rdbms.mapper;

import io.bricksets.rdbms.tables.records.TagRecord;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.domain.AggregateId;

public enum TagMapper {

    INSTANCE;

    public AggregateId map(final TagRecord record) {
        if (record.getTagClass().equals(BricksetId.class.getSimpleName())) {
            return BricksetId.fromString(record.getValue());
        }
        throw new IllegalArgumentException(String.format("Cannot map TagRecord (%s) to AggregateId", record.getTagClass()));
    }
}