package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.domain.brickset.Brickset;
import be.koder.bricksets.domain.brickset.event.BricksetRemoved;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.api.brickset.RemoveBricksetPresenter;
import be.koder.bricksets.inmemory.InMemoryBricksetRepository;
import be.koder.bricksets.inmemory.InMemoryEventPubSub;
import be.koder.bricksets.test.MockTimeService;
import be.koder.bricksets.vocabulary.brickset.BricksetId;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.time.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a use case to remove Bricksets")
public class RemoveBricksetUseCaseTest {

    private final InMemoryBricksetRepository bricksetRepository = new InMemoryBricksetRepository();
    private final InMemoryEventPubSub eventPublisher = new InMemoryEventPubSub();
    private final TimeService timeService = new MockTimeService();
    private final RemoveBricksetUseCase useCase = new RemoveBricksetUseCase(bricksetRepository, timeService, eventPublisher);

    @Nested
    @DisplayName("when Brickset successfully removed")
    class TestHappyFlow implements RemoveBricksetPresenter {

        private final BricksetNumber number = BricksetNumber.fromString("10194");
        private final BricksetTitle title = BricksetTitle.fromString("Emerald Night");
        private final Brickset brickset = Brickset.create(number, title, timeService);
        private boolean removedCalled;

        @BeforeEach
        void setup() {
            bricksetRepository.save(brickset);
            useCase.removeBrickset(brickset.getId(), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(removedCalled);
        }

        @Test
        @DisplayName("it should persist state")
        void statePersisted() {
            assertThat(bricksetRepository.get(brickset.getId())).isEmpty();
        }

        @Test
        @DisplayName("it should publish events")
        void eventsPublished() {
            eventPublisher.verifyEvents(List.of(
                    new BricksetRemoved(
                            Timestamp.fromString("2023-04-04T16:31:00.000Z"),
                            brickset.getId()
                    )
            ));
        }

        @Override
        public void removed(BricksetId bricksetId) {
            removedCalled = true;
        }

        @Override
        public void bricksetNotFound() {
            fail();
        }
    }

    @Nested
    @DisplayName("when Brickset not found")
    class TestWhenBricksetNotFound implements RemoveBricksetPresenter {

        private boolean bricksetNotFoundCalled;

        @BeforeEach
        void setup() {
            useCase.removeBrickset(BricksetId.createNew(), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(bricksetNotFoundCalled);
        }

        @Override
        public void removed(BricksetId bricksetId) {
            fail();
        }

        @Override
        public void bricksetNotFound() {
            bricksetNotFoundCalled = true;
        }
    }
}