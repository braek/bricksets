package io.bricksets.usecase.brickset;

import io.bricksets.domain.command.Command;
import io.bricksets.vocabulary.brickset.BricksetId;

public record RemoveBricksetCommand(BricksetId bricksetId) implements Command {
}