package io.bricksets.usecase.brickset;

import io.bricksets.domain.command.Command;
import io.bricksets.vocabulary.brickset.BricksetId;

import static java.util.Objects.requireNonNull;

public record RemoveBricksetCommand(BricksetId bricksetId) implements Command {
    public RemoveBricksetCommand {
        requireNonNull(bricksetId);
    }
}