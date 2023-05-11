package io.bricksets.system;

import io.bricksets.domain.time.TimeService;
import io.bricksets.vocabulary.time.Timestamp;

import java.time.Instant;

public final class SystemTimeService implements TimeService {
    @Override
    public Timestamp now() {
        return Timestamp.fromInstant(Instant.now());
    }
}