package io.bricksets.usecase.brickset;

import io.bricksets.domain.command.Command;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetTitle;

import static java.util.Objects.requireNonNull;

public record ModifyBricksetCommand(BricksetId bricksetId, BricksetTitle title) implements Command {
    public ModifyBricksetCommand {
        requireNonNull(bricksetId);
        requireNonNull(title);
    }
}