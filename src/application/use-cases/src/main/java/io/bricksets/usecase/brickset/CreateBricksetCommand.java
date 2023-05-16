package io.bricksets.usecase.brickset;

import io.bricksets.domain.command.Command;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public record CreateBricksetCommand(BricksetNumber number, BricksetTitle title) implements Command {
}