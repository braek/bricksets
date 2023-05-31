package be.koder.bricksets.config;

import be.koder.bricksets.api.brickset.CreateBrickset;
import be.koder.bricksets.api.brickset.ModifyBrickset;
import be.koder.bricksets.api.brickset.RemoveBrickset;
import be.koder.bricksets.domain.brickset.BricksetNumberService;
import be.koder.bricksets.domain.brickset.BricksetRepository;
import be.koder.bricksets.domain.event.EventPublisher;
import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.usecase.brickset.CreateBricksetUseCase;
import be.koder.bricksets.usecase.brickset.ModifyBricksetUseCase;
import be.koder.bricksets.usecase.brickset.RemoveBricksetUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    CreateBrickset createBrickset(BricksetRepository bricksetRepository, BricksetNumberService bricksetNumberService, TimeService timeService, EventPublisher eventPublisher) {
        return new CreateBricksetUseCase(bricksetRepository, bricksetNumberService, timeService, eventPublisher);
    }

    @Bean
    ModifyBrickset modifyBrickset(BricksetRepository bricksetRepository, TimeService timeService, EventPublisher eventPublisher) {
        return new ModifyBricksetUseCase(bricksetRepository, timeService, eventPublisher);
    }

    @Bean
    RemoveBrickset removeBrickset(BricksetRepository bricksetRepository, TimeService timeService, EventPublisher eventPublisher) {
        return new RemoveBricksetUseCase(bricksetRepository, timeService, eventPublisher);
    }
}