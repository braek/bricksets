package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.api.brickset.CreateBricksetPresenter;
import be.koder.bricksets.domain.brickset.Brickset;
import be.koder.bricksets.domain.brickset.event.BricksetCreated;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.inmemory.InMemoryBricksetRepository;
import be.koder.bricksets.inmemory.InMemoryEventPubSub;
import be.koder.bricksets.test.MockCreateBricksetPresenter;
import be.koder.bricksets.test.MockTimeService;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.time.Timestamp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a use case to create Bricksets")
class CreateBricksetUseCaseTest {

    private final InMemoryBricksetRepository bricksetRepository = new InMemoryBricksetRepository();
    private final InMemoryEventPubSub eventPublisher = new InMemoryEventPubSub();
    private final TimeService timeService = new MockTimeService();
    private final CreateBricksetUseCase useCase = new CreateBricksetUseCase(bricksetRepository, bricksetRepository, timeService, eventPublisher);

    @Nested
    @DisplayName("when Brickset successfully created")
    class TestHappyFlow implements CreateBricksetPresenter {

        private final BricksetNumber number = BricksetNumber.fromString("40580");
        private final BricksetTitle title = BricksetTitle.fromString("Blacktron Cruiser");
        private final Timestamp createdOn = Timestamp.fromString("2023-04-04T16:30:00Z");

        private boolean createdCalled;
        private BricksetId bricksetId;
        private Brickset brickset;

        @BeforeEach
        void setup() {
            useCase.createBrickset(number, title, this);
            brickset = bricksetRepository.get(bricksetId).orElseThrow();
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(createdCalled);
        }

        @Test
        @DisplayName("it should persist state")
        void statePersisted() {
            Assertions.assertThat(brickset.getId()).isEqualTo(bricksetId);
            Assertions.assertThat(brickset.getTitle()).isEqualTo(title);
            Assertions.assertThat(brickset.getNumber()).isEqualTo(number);
            Assertions.assertThat(brickset.getCreatedOn()).isEqualTo(createdOn);
            Assertions.assertThat(brickset.getModifiedOn()).isEqualTo(createdOn);
        }

        @Test
        @DisplayName("it should publish events")
        void eventsPublished() {
            eventPublisher.verifyEvents(List.of(
                    new BricksetCreated(
                            createdOn,
                            bricksetId,
                            number,
                            title
                    )
            ));
        }

        @Override
        public void created(BricksetId bricksetId) {
            this.createdCalled = true;
            this.bricksetId = bricksetId;
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
            useCase.createBrickset(number, title, new MockCreateBricksetPresenter());
            useCase.createBrickset(number, title, this);
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