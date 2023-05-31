package be.koder.bricksets.usecase;

import be.koder.bricksets.domain.command.Command;

public interface UseCase<COMMAND extends Command, PRESENTER> {
    void execute(COMMAND command, PRESENTER presenter);
}