package io.bricksets.vocabulary.brickset;

import io.bricksets.vocabulary.time.Timestamp;

public record BricksetListItem(
        BricksetId id,
        BricksetNumber number,
        BricksetTitle title,
        Timestamp createdOn,
        Timestamp modifiedOn
) {
}