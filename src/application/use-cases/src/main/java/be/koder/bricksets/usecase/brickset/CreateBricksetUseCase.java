package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.api.brickset.CreateBrickset;
import be.koder.bricksets.api.brickset.CreateBricksetPresenter;
import be.koder.bricksets.domain.brickset.Brickset;
import be.koder.bricksets.domain.brickset.BricksetNumberService;
import be.koder.bricksets.domain.brickset.BricksetRepository;
import be.koder.bricksets.domain.event.EventPublisher;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.usecase.UseCase;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;

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