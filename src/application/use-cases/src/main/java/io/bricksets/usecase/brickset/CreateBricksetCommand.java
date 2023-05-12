package io.bricksets.usecase.brickset;

import io.bricksets.domain.command.Command;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;

import static java.util.Objects.requireNonNull;

public record CreateBricksetCommand(BricksetNumber number, BricksetTitle title) implements Command {
    public CreateBricksetCommand {
        requireNonNull(number);
        requireNonNull(title);
    }
}