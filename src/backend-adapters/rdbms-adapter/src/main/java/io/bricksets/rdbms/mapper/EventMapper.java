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

    public Event map(final EventRecord event, final List<TagRecord> tags) {

        // Create tags
        final Set<AggregateId> aggregateIds = new HashSet<>();
        tags.forEach(it -> aggregateIds.add(TagMapper.INSTANCE.map(it)));

        // BricksetCreated
        if (event.getEventClass().equals(BricksetCreated.class.getSimpleName())) {
            var content = deserialize(event.getEventValue(), BricksetCreated.class);
            return new BricksetCreated(
                    EventId.fromUuid(event.getId()),
                    Timestamp.fromLocalDateTime(event.getOccurredOn()),
                    aggregateIds,
                    content.bricksetId(),
                    content.number(),
                    content.title()
            );
        }

        // BricksetModified
        if (event.getEventClass().equals(BricksetModified.class.getSimpleName())) {
            var content = deserialize(event.getEventValue(), BricksetModified.class);
            return new BricksetModified(
                    EventId.fromUuid(event.getId()),
                    Timestamp.fromLocalDateTime(event.getOccurredOn()),
                    aggregateIds,
                    content.bricksetId(),
                    content.title()
            );
        }

        // BricksetRemoved
        if (event.getEventClass().equals(BricksetRemoved.class.getSimpleName())) {
            var content = deserialize(event.getEventValue(), BricksetRemoved.class);
            return new BricksetRemoved(
                    EventId.fromUuid(event.getId()),
                    Timestamp.fromLocalDateTime(event.getOccurredOn()),
                    aggregateIds,
                    content.bricksetId()
            );
        }

        // Throw exception
        throw new IllegalArgumentException(String.format("Cannot map EventRecord (%s) to Event", event.getEventClass()));
    }
}