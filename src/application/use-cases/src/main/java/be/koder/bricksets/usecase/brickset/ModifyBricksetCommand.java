package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.domain.command.Command;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;

public record ModifyBricksetCommand(BricksetId bricksetId, BricksetTitle title) implements Command {
}