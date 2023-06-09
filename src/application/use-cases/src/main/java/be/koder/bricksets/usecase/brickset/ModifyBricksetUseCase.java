package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.api.brickset.ModifyBrickset;
import be.koder.bricksets.api.brickset.ModifyBricksetPresenter;
import be.koder.bricksets.domain.brickset.BricksetRepository;
import be.koder.bricksets.domain.event.EventPublisher;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.usecase.UseCase;
import be.koder.bricksets.vocabulary.brickset.BricksetId;

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
            eventPublisher.publish(it.getMutations());
            presenter.modified(command.bricksetId());
        }, presenter::bricksetNotFound);
    }
}