package io.bricksets.usecase.brickset;

import io.bricksets.api.brickset.CreateBrickset;
import io.bricksets.api.brickset.CreateBricksetPresenter;
import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.domain.time.TimeService;
import io.bricksets.usecase.UseCase;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;

public final class CreateBricksetUseCase implements CreateBrickset, UseCase<CreateBricksetCommand, CreateBricksetPresenter> {

    private final BricksetRepository bricksetRepository;
    private final BricksetNumberService bricksetNumberService;
    private final TimeService timeService;
    private final EventPublisher eventPublisher;

    public CreateBricksetUseCase(BricksetRepository bricksetRepository, BricksetNumberService bricksetNumberService, TimeService timeService, EventPublisher eventPublisher) {
        this.bricksetRepository = bricksetRepository;
        this.bricksetNumberService = bricksetNumberService;
        this.timeService = timeService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void createBrickset(BricksetNumber number, BricksetTitle title, CreateBricksetPresenter presenter) {
        execute(new CreateBricksetCommand(number, title), presenter);
    }

    @Override
    public void execute(CreateBricksetCommand command, CreateBricksetPresenter presenter) {
        // TODO: put this logic in aggregate, someday, somehow?
        if (bricksetNumberService.exists(command.number())) {
            presenter.bricksetNumberAlreadyExists();
            return;
        }
        final Brickset brickset = Brickset.create(command.number(), command.title(), timeService);
        bricksetRepository.save(brickset);
        eventPublisher.publish(brickset.getMutations());
        presenter.created(brickset.getId());
    }
}