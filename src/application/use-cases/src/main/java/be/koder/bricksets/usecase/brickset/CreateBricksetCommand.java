package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.domain.command.Command;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;

public record CreateBricksetCommand(BricksetNumber number, BricksetTitle title) implements Command {
}