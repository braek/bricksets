package io.bricksets.usecase;

import io.bricksets.domain.command.Command;

public interface UseCase<COMMAND extends Command, PRESENTER> {
    void execute(COMMAND command, PRESENTER presenter);
}