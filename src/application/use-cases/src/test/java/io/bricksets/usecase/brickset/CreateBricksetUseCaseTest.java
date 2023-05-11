package io.bricksets.usecase.brickset;

import io.bricksets.test.InMemoryBricksetRepository;
import io.bricksets.test.InMemoryEventPublisher;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Given a use case to create Bricksets")
class CreateBricksetUseCaseTest {

    private final InMemoryBricksetRepository bricksetRepository = new InMemoryBricksetRepository();
    private final InMemoryEventPublisher eventPublisher = new InMemoryEventPublisher();
    private final CreateBricksetUseCase useCase = new CreateBricksetUseCase(bricksetRepository, bricksetRepository, eventPublisher);
}