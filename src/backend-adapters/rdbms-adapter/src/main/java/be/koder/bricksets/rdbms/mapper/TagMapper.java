package be.koder.bricksets.rdbms.mapper;

import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.domain.AggregateId;
import be.koder.bricksets.rdbms.tables.records.TagRecord;

public enum TagMapper {

    INSTANCE;

    public AggregateId map(final TagRecord record) {
        if (record.getClazz().equals(BricksetId.class.getSimpleName())) {
            return BricksetId.fromString(record.getValue());
        }
        throw new IllegalArgumentException(String.format("Cannot map TagRecord (%s) to AggregateId", record.getClazz()));
    }
}