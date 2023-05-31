package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.api.brickset.RemoveBrickset;
import be.koder.bricksets.api.brickset.RemoveBricksetPresenter;
import be.koder.bricksets.domain.brickset.BricksetRepository;
import be.koder.bricksets.domain.event.EventPublisher;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.usecase.UseCase;
import be.koder.bricksets.vocabulary.brickset.BricksetId;

public final class RemoveBricksetUseCase implements RemoveBrickset, UseCase<RemoveBricksetCommand, RemoveBricksetPresenter> {

    private final BricksetRepository bricksetRepository;
    private final TimeService timeService;
    private final EventPublisher eventPublisher;

    public RemoveBricksetUseCase(BricksetRepository bricksetRepository, TimeService timeService, EventPublisher eventPublisher) {
        this.bricksetRepository = bricksetRepository;
        this.timeService = timeService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void removeBrickset(BricksetId bricksetId, RemoveBricksetPresenter presenter) {
        execute(new RemoveBricksetCommand(bricksetId), presenter);
    }

    @Override
    public void execute(RemoveBricksetCommand command, RemoveBricksetPresenter presenter) {
        bricksetRepository.get(command.bricksetId()).ifPresentOrElse(it -> {
            it.remove(timeService);
            bricksetRepository.save(it);
            eventPublisher.publish(it.getMutations());
            presenter.removed(command.bricksetId());
        }, presenter::bricksetNotFound);
    }
}