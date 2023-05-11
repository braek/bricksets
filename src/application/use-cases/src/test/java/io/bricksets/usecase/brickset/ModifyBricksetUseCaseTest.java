package io.bricksets.usecase.brickset;

import io.bricksets.api.ModifyBricksetPresenter;
import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.brickset.BricksetModified;
import io.bricksets.domain.time.TimeService;
import io.bricksets.inmemory.InMemoryBricksetRepository;
import io.bricksets.inmemory.InMemoryEventPublisher;
import io.bricksets.test.MockTimeService;
import io.bricksets.vocabulary.brickset.BricksetId;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import io.bricksets.vocabulary.time.Timestamp;
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
    private final InMemoryEventPublisher eventPublisher = new InMemoryEventPublisher();
    private final TimeService timeService = new MockTimeService();
    private final ModifyBricksetUseCase useCase = new ModifyBricksetUseCase(bricksetRepository, timeService, eventPublisher);

    @Nested
    @DisplayName("when Brickset successfully created")
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
            assertThat(updatedBrickset.getCreatedAt()).isEqualTo(Timestamp.fromString("2023-04-04T16:30:00.000Z"));
            assertThat(updatedBrickset.getModifiedAt()).isEqualTo(Timestamp.fromString("2023-04-04T16:31:00.000Z"));
            assertThat(updatedBrickset.getRemovedAt()).isNull();
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
}