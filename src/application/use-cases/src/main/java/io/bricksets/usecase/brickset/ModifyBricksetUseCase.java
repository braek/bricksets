package io.bricksets.usecase.brickset;

import io.bricksets.api.ModifyBrickset;
import io.bricksets.api.ModifyBricksetPresenter;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.domain.time.TimeService;
import io.bricksets.usecase.UseCase;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public final class ModifyBricksetUseCase implements ModifyBrickset, UseCase<ModifyBricksetCommand, ModifyBricksetPresenter> {

    private final BricksetRepository bricksetRepository;
    private final TimeService timeService;
    private final EventPublisher eventPublisher;

    public ModifyBricksetUseCase(BricksetRepository bricksetRepository, TimeService timeService, EventPublisher eventPublisher) {
        this.bricksetRepository = bricksetRepository;
        this.timeService = timeService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void modifyBrickset(BricksetId bricksetId, BricksetTitle title, ModifyBricksetPresenter presenter) {
        execute(new ModifyBricksetCommand(bricksetId, title), presenter);
    }

    @Override
    public void execute(ModifyBricksetCommand command, ModifyBricksetPresenter presenter) {
        bricksetRepository.get(command.bricksetId()).ifPresentOrElse(it -> {
            it.modify(command.title(), timeService);
            bricksetRepository.save(it);
            eventPublisher.publish(it.getMutatingEvents());
            presenter.modified(command.bricksetId());
        }, presenter::bricksetNotFound);
    }
}