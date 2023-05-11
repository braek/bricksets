package io.bricksets.usecase.brickset;

import io.bricksets.api.RemoveBrickset;
import io.bricksets.api.RemoveBricksetPresenter;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.usecase.UseCase;
import io.bricksets.vocabulary.brickset.BricksetId;

import static java.util.Objects.requireNonNull;

public final class RemoveBricksetUseCase implements RemoveBrickset, UseCase<RemoveBricksetCommand, RemoveBricksetPresenter> {

    private final BricksetRepository bricksetRepository;
    private final EventPublisher eventPublisher;

    public RemoveBricksetUseCase(BricksetRepository bricksetRepository, EventPublisher eventPublisher) {
        this.bricksetRepository = bricksetRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void removeBrickset(BricksetId bricksetId, RemoveBricksetPresenter presenter) {
        requireNonNull(bricksetId, "ID cannot be NULL");
        requireNonNull(presenter, "Presenter cannot be NULL");
        execute(new RemoveBricksetCommand(bricksetId), presenter);
    }

    @Override
    public void execute(RemoveBricksetCommand command, RemoveBricksetPresenter presenter) {
        bricksetRepository.get(command.bricksetId()).ifPresentOrElse(it -> {
            it.remove();
            bricksetRepository.save(it);
            eventPublisher.publish(it.getMutatingEvents());
            presenter.removed(command.bricksetId());
        }, presenter::bricksetNotFound);
    }
}