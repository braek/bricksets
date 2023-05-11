package io.bricksets.domain.time;

import io.bricksets.vocabulary.time.Timestamp;

public interface TimeService {
    Timestamp now();
}