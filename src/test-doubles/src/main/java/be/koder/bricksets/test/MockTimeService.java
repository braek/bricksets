package be.koder.bricksets.test;

import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.vocabulary.time.Timestamp;

import java.time.Instant;

// Deterministic TimeService for testing
public final class MockTimeService implements TimeService {

    private Instant pointer = Instant.parse("2023-04-04T16:30:00.000Z");

    @Override
    public Timestamp now() {
        var timestamp = Timestamp.fromInstant(pointer);
        pointer = pointer.plusSeconds(60);
        return timestamp;
    }
}