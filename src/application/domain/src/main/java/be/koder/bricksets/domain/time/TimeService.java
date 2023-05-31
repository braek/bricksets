package be.koder.bricksets.domain.time;

import be.koder.bricksets.vocabulary.time.Timestamp;

public interface TimeService {
    Timestamp now();
}