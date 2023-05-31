package be.koder.bricksets.system;

import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.vocabulary.time.Timestamp;

import java.time.Instant;
import java.time.ZoneOffset;

public final class SystemTimeService implements TimeService {
    @Override
    public Timestamp now() {
        return Timestamp.fromInstant(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
    }
}