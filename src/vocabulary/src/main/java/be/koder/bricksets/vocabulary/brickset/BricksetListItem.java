package be.koder.bricksets.vocabulary.brickset;

import be.koder.bricksets.vocabulary.time.Timestamp;

public record BricksetListItem(
        BricksetId id,
        BricksetNumber number,
        BricksetTitle title,
        Timestamp createdOn,
        Timestamp modifiedOn
) {
}