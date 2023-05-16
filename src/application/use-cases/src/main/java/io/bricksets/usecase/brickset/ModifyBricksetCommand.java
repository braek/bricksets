package io.bricksets.usecase.brickset;

import io.bricksets.domain.command.Command;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public record ModifyBricksetCommand(BricksetId bricksetId, BricksetTitle title) implements Command {
}