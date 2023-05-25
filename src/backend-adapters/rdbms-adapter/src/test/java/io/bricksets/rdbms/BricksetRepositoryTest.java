package io.bricksets.rdbms;

import io.bricksets.domain.brickset.Brickset;
import io.bricksets.domain.time.TimeService;
import io.bricksets.vocabulary.brickset.BricksetNumber;
import io.bricksets.vocabulary.brickset.BricksetTitle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(initializers = {TestContextInitializer.class})
public class BricksetRepositoryTest {

    @Autowired
    private RdbmsBricksetRepository bricksetRepository;

    @Autowired
    private TimeService timeService;

    @Test
    void testIt() {
        bricksetRepository.save(Brickset.create(
                BricksetNumber.fromString("12345"),
                BricksetTitle.fromString("Kristof Test"),
                timeService
        ));
        assertTrue(false);
    }
}