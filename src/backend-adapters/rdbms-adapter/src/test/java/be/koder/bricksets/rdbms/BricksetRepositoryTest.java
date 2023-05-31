package be.koder.bricksets.rdbms;

import be.koder.bricksets.domain.brickset.Brickset;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.rdbms.repository.RdbmsBricksetRepository;
import be.koder.bricksets.vocabulary.brickset.BricksetNumber;
import be.koder.bricksets.vocabulary.brickset.BricksetTitle;
import be.koder.bricksets.vocabulary.time.Timestamp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BricksetRepositoryTest {

    @Autowired
    private RdbmsBricksetRepository bricksetRepository;

    @Autowired
    private TimeService timeService;

    @Test
    void testCreation() {
        final var number = BricksetNumber.fromString("12345");
        final var title = BricksetTitle.fromString("Kristof Test");
        final var brickset = Brickset.create(
                number,
                title,
                timeService
        );
        final var bricksetId = brickset.getId();
        bricksetRepository.save(brickset);
        var persisted = bricksetRepository.get(brickset.getId());
        assertThat(persisted).hasValueSatisfying(it -> {
            Assertions.assertThat(it.getId()).isEqualTo(bricksetId);
            Assertions.assertThat(it.getNumber()).isEqualTo(number);
            Assertions.assertThat(it.getTitle()).isEqualTo(title);
            Assertions.assertThat(it.getCreatedOn()).isEqualTo(Timestamp.fromString("2023-04-04T16:30:00.000Z"));
            Assertions.assertThat(it.getModifiedOn()).isEqualTo(Timestamp.fromString("2023-04-04T16:30:00.000Z"));
            assertTrue(it.getMutations().isEmpty());
            Assertions.assertThat(it.getLastEventId()).isNotNull();
        });
    }
}