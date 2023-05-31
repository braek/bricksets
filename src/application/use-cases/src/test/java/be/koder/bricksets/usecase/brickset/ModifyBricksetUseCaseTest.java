package be.koder.bricksets.usecase.brickset;

import be.koder.bricksets.api.brickset.ModifyBricksetPresenter;
import be.koder.bricksets.domain.brickset.Brickset;
import be.koder.bricksets.domain.brickset.event.BricksetModified;
import be.koder.bricksets.domain.time.TimeService;
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

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a use case to modify Bricksets")
public class ModifyBricksetUseCaseTest {

    private final InMemoryBricksetRepository bricksetRepository = new InMemoryBricksetRepository();
    private final InMemoryEventPubSub eventPublisher = new InMemoryEventPubSub();
    private final TimeService timeService = new MockTimeService();
    private final ModifyBricksetUseCase useCase = new ModifyBricksetUseCase(bricksetRepository, timeService, eventPublisher);

    @Nested
    @DisplayName("when Brickset successfully modified")
    class TestHappyFlow implements ModifyBricksetPresenter {

        private final BricksetNumber number = BricksetNumber.fromString("10194");
        private final BricksetTitle title = BricksetTitle.fromString("Emerald Night");
        private final Brickset brickset = Brickset.create(number, title, timeService);
        private final BricksetTitle newTitle = BricksetTitle.fromString("Crocodile Locomotive");
        private boolean modifiedCalled;
        private Brickset updatedBrickset;

        @BeforeEach
        void setup() {
            bricksetRepository.save(brickset);
            useCase.modifyBrickset(brickset.getId(), newTitle, this);
            updatedBrickset = bricksetRepository.get(brickset.getId()).orElseThrow();
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(modifiedCalled);
        }

        @Test
        @DisplayName("it should persist state")
        void statePersisted() {
            assertThat(updatedBrickset.getId()).isEqualTo(brickset.getId());
            assertThat(updatedBrickset.getNumber()).isEqualTo(number);
            assertThat(updatedBrickset.getTitle()).isEqualTo(newTitle);
            assertThat(updatedBrickset.getCreatedOn()).isEqualTo(Timestamp.fromString("2023-04-04T16:30:00.000Z"));
            assertThat(updatedBrickset.getModifiedOn()).isEqualTo(Timestamp.fromString("2023-04-04T16:31:00.000Z"));
        }

        @Test
        @DisplayName("it should publish events")
        void eventsPublished() {
            eventPublisher.verifyEvents(List.of(
                    new BricksetModified(
                            Timestamp.fromString("2023-04-04T16:31:00.000Z"),
                            brickset.getId(),
                            newTitle
                    )
            ));
        }

        @Override
        public void modified(BricksetId bricksetId) {
            modifiedCalled = true;
        }

        @Override
        public void bricksetNotFound() {
            fail();
        }
    }

    @Nested
    @DisplayName("when Brickset not found")
    class TestWhenBricksetNotFound implements ModifyBricksetPresenter {

        private boolean bricksetNotFoundCalled;

        @BeforeEach
        void setup() {
            useCase.modifyBrickset(BricksetId.createNew(), BricksetTitle.fromString("Maersk Train"), this);
        }

        @Test
        @DisplayName("it should provide feedback")
        void feedbackProvided() {
            assertTrue(bricksetNotFoundCalled);
        }

        @Override
        public void modified(BricksetId bricksetId) {
            fail();
        }

        @Override
        public void bricksetNotFound() {
            bricksetNotFoundCalled = true;
        }
    }
}