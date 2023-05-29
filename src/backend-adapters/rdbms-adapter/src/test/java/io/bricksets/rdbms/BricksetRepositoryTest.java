package io.bricksets.rdbms;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.time.TimeService;
import io.bricksets.rdbms.repository.RdbmsBricksetRepository;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
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
            assertThat(it.getId()).isEqualTo(bricksetId);
            assertThat(it.getNumber()).isEqualTo(number);
            assertThat(it.getTitle()).isEqualTo(title);
            assertThat(it.getModifiedOn()).isNull();
            assertTrue(it.getMutations().isEmpty());
            assertThat(it.getLastEventId()).isNotNull();
        });
    }
}