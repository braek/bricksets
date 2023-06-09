package be.koder.bricksets.rdbms.mapper;

import be.koder.bricksets.vocabulary.domain.event.EventId;
import be.koder.bricksets.vocabulary.time.Timestamp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.koder.bricksets.domain.brickset.event.BricksetCreated;
import be.koder.bricksets.domain.brickset.event.BricksetModified;
import be.koder.bricksets.domain.brickset.event.BricksetRemoved;
import be.koder.bricksets.domain.event.Event;
import be.koder.bricksets.rdbms.tables.records.EventRecord;
import be.koder.bricksets.rdbms.tables.records.TagRecord;
import be.koder.bricksets.vocabulary.domain.AggregateId;
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

    private <T extends Event> T deserialize(final JSONB json, final Class<T> clazz) {
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
        if (event.getType().equals(BricksetCreated.class.getSimpleName())) {
            var content = deserialize(event.getContent(), BricksetCreated.class);
            return new BricksetCreated(
                    EventId.fromUuid(event.getId()),
                    Timestamp.fromLocalDateTime(event.getOccurredOn()),
                    aggregateIds,
                    content.number(),
                    content.title()
            );
        }

        // BricksetModified
        if (event.getType().equals(BricksetModified.class.getSimpleName())) {
            var content = deserialize(event.getContent(), BricksetModified.class);
            return new BricksetModified(
                    EventId.fromUuid(event.getId()),
                    Timestamp.fromLocalDateTime(event.getOccurredOn()),
                    aggregateIds,
                    content.title()
            );
        }

        // BricksetRemoved
        if (event.getType().equals(BricksetRemoved.class.getSimpleName())) {
            return new BricksetRemoved(
                    EventId.fromUuid(event.getId()),
                    Timestamp.fromLocalDateTime(event.getOccurredOn()),
                    aggregateIds
            );
        }

        // Throw exception
        throw new IllegalArgumentException(String.format("Cannot map EventRecord (%s) to Event", event.getType()));
    }
}