package be.koder.bricksets.rdbms.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import be.koder.bricksets.vocabulary.domain.AggregateId;
import be.koder.bricksets.vocabulary.domain.event.EventId;
import be.koder.bricksets.vocabulary.time.Timestamp;

import java.util.Set;

// Make sure that ID, occurred on and tags are ignored from Event content, because these are separate columns or tables
public abstract class EventMixin {

    @JsonIgnore
    abstract EventId id();

    @JsonIgnore
    abstract Timestamp occurredOn();

    @JsonIgnore
    abstract Set<AggregateId> tags();
}