package io.bricksets.usecase.brickset;

import io.bricksets.api.CreateBricksetPresenter;
import io.bricksets.test.InMemoryBricksetRepository;
import io.bricksets.test.InMemoryEventPublisher;
import io.bricksets.test.MockCreateBricksetPresenter;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a use case to create Bricksets")
class CreateBricksetUseCaseTest {

    private final InMemoryBricksetRepository bricksetRepository = new InMemoryBricksetRepository();
    private final InMemoryEventPublisher eventPublisher = new InMemoryEventPublisher();
    private final CreateBricksetUseCase useCase = new CreateBricksetUseCase(bricksetRepository, bricksetRepository, eventPublisher);

    @Nested
    @DisplayName("when Brickset successfully created")
    class TestHappyFlow implements CreateBricksetPresenter {

        private boolean createdCalled;
        private final BricksetNumber number = BricksetNumber.fromString("40580");
        private final BricksetTitle title = BricksetTitle.fromString("Blacktron Cruiser");

        @BeforeEach
        void setup() {
            useCase.execute(new CreateBricksetCommand(number, title), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(createdCalled);
        }

        @Override
        public void created(BricksetId bricksetId) {
            createdCalled = true;
        }

        @Override
        public void bricksetNumberAlreadyExists() {
            fail();
        }
    }

    @Nested
    @DisplayName("when Brickset number already exists")
    class TestWhenBricksetNumberAlreadyExists implements CreateBricksetPresenter {

        private boolean bricksetNumberAlreadyExistsCalled;
        private final BricksetNumber number = BricksetNumber.fromString("10497");
        private final BricksetTitle title = BricksetTitle.fromString("Galaxy Explorer");

        @BeforeEach
        void setup() {
            useCase.execute(new CreateBricksetCommand(number, title), new MockCreateBricksetPresenter());
            useCase.execute(new CreateBricksetCommand(number, title), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(bricksetNumberAlreadyExistsCalled);
        }

        @Override
        public void created(BricksetId bricksetId) {
            fail();
        }

        @Override
        public void bricksetNumberAlreadyExists() {
            bricksetNumberAlreadyExistsCalled = true;
        }
    }
}