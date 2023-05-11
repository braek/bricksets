package io.bricksets.usecase.brickset;

import io.bricksets.api.CreateBrickset;
import io.bricksets.api.CreateBricksetPresenter;
import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.usecase.UseCase;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public final class CreateBricksetUseCase implements CreateBrickset, UseCase<CreateBricksetCommand, CreateBricksetPresenter> {

    private final BricksetRepository bricksetRepository;
    private final BricksetNumberService bricksetNumberService;
    private final EventPublisher eventPublisher;

    public CreateBricksetUseCase(BricksetRepository bricksetRepository, BricksetNumberService bricksetNumberService, EventPublisher eventPublisher) {
        this.bricksetRepository = bricksetRepository;
        this.bricksetNumberService = bricksetNumberService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void createBrickset(BricksetNumber number, BricksetTitle title, CreateBricksetPresenter presenter) {
        execute(new CreateBricksetCommand(number, title), presenter);
    }

    @Override
    public void execute(CreateBricksetCommand command, CreateBricksetPresenter presenter) {
        // TODO: put this logic in aggregate, somehow?
        if (bricksetNumberService.exists(command.number())) {
            presenter.bricksetNumberAlreadyExists();
            return;
        }
        final Brickset brickset = Brickset.create(command.number(), command.title());
        bricksetRepository.save(brickset);
        eventPublisher.publish(brickset.getMutatingEvents());
    }
}