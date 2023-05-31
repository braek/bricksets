package io.bricksets.usecase.brickset;

import io.bricksets.api.brickset.RemoveBrickset;
import io.bricksets.api.brickset.RemoveBricksetPresenter;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.domain.time.TimeService;
import io.bricksets.usecase.UseCase;
import io.bricksets.vocabulary.brickset.BricksetId;

public final class RemoveBricksetUseCase implements RemoveBrickset, UseCase<RemoveBricksetCommand, RemoveBricksetPresenter> {

    private final BricksetRepository bricksetRepository;
    private final TimeService timeService;
    private final EventPublisher eventPublisher;

    public RemoveBricksetUseCase(final BricksetRepository bricksetRepository, final TimeService timeService, final EventPublisher eventPublisher) {
        this.bricksetRepository = bricksetRepository;
        this.timeService = timeService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void removeBrickset(final BricksetId bricksetId, final RemoveBricksetPresenter presenter) {
        execute(new RemoveBricksetCommand(bricksetId), presenter);
    }

    @Override
    public void execute(final RemoveBricksetCommand command, final RemoveBricksetPresenter presenter) {
        bricksetRepository.get(command.bricksetId()).ifPresentOrElse(it -> {
            it.remove(timeService);
            bricksetRepository.save(it);
            eventPublisher.publish(it.getMutations());
            presenter.removed(command.bricksetId());
        }, presenter::bricksetNotFound);
    }
}