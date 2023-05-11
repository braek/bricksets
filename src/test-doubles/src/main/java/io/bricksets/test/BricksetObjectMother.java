package io.bricksets.test;

import io.bricksets.domain.brickset.BricksetCreated;
import io.bricksets.domain.event.EventStream;
import io.bricksets.domain.time.TimeService;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;

import java.util.List;

public enum BricksetObjectMother {

    INSTANCE;

    public EventStream buildEventStream(BricksetId bricksetId, BricksetNumber number, BricksetTitle title, TimeService timeService) {
        return new EventStream(List.of(
                new BricksetCreated(timeService.now(), bricksetId, number, title)
        ));
    }
}