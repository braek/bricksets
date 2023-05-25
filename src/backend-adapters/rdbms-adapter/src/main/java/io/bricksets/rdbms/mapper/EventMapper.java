package io.bricksets.rdbms.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bricksets.domain.brickset.event.BricksetCreated;
import io.bricksets.domain.brickset.event.BricksetModified;
import io.bricksets.domain.brickset.event.BricksetRemoved;
import io.bricksets.domain.event.Event;
import io.bricksets.rdbms.tables.records.EventRecord;
import io.bricksets.rdbms.tables.records.TagRecord;
import io.bricksets.vocabulary.domain.AggregateId;
import io.bricksets.vocabulary.domain.event.EventId;
import io.bricksets.vocabulary.time.Timestamp;
import org.jooq.JSONB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public <T extends Event> T deserialize(final JSONB json, final Class<T> clazz) {
        try {
            return objectMapper.readValue(json.data(), clazz);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Event map(final EventRecord eventRecord, final List<TagRecord> tagRecords) {
        final Set<AggregateId> tags = new HashSet<>();
        tagRecords.forEach(it -> tags.add(TagMapper.INSTANCE.map(it)));
        if (eventRecord.getEventClass().equals(BricksetCreated.class.getSimpleName())) {
            var event = deserialize(eventRecord.getEventValue(), BricksetCreated.class);
            return new BricksetCreated(
                    EventId.fromUuid(eventRecord.getId()),
                    Timestamp.fromLocalDateTime(eventRecord.getOccurredOn()),
                    tags,
                    event.bricksetId(),
                    event.number(),
                    event.title()
            );
        }
        if (eventRecord.getEventClass().equals(BricksetModified.class.getSimpleName())) {
            var event = deserialize(eventRecord.getEventValue(), BricksetModified.class);
            return new BricksetModified(
                    EventId.fromUuid(eventRecord.getId()),
                    Timestamp.fromLocalDateTime(eventRecord.getOccurredOn()),
                    tags,
                    event.bricksetId(),
                    event.title()
            );
        }
        if (eventRecord.getEventClass().equals(BricksetRemoved.class.getSimpleName())) {
            var event = deserialize(eventRecord.getEventValue(), BricksetRemoved.class);
            return new BricksetRemoved(
                    EventId.fromUuid(eventRecord.getId()),
                    Timestamp.fromLocalDateTime(eventRecord.getOccurredOn()),
                    tags,
                    event.bricksetId()
            );
        }
        throw new IllegalArgumentException("Cannot map EventRecord to Event");
    }
}