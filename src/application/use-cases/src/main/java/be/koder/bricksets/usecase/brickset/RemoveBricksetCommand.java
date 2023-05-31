package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.domain.command.Command;
import be.koder.bricksets.vocabulary.brickset.BricksetId;

public record RemoveBricksetCommand(BricksetId bricksetId) implements Command {
}