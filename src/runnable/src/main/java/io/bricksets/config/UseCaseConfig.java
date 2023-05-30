package io.bricksets.config;

import io.bricksets.api.CreateBrickset;
import io.bricksets.api.ModifyBrickset;
import io.bricksets.api.RemoveBrickset;
import io.bricksets.domain.brickset.BricksetNumberService;
import io.bricksets.domain.brickset.BricksetRepository;
import io.bricksets.domain.event.EventProjector;
import io.bricksets.domain.event.EventPublisher;
import io.bricksets.domain.time.TimeService;
import io.bricksets.policy.ProjectorPolicy;
import io.bricksets.usecase.brickset.CreateBricksetUseCase;
import io.bricksets.usecase.brickset.ModifyBricksetUseCase;
import io.bricksets.usecase.brickset.RemoveBricksetUseCase;
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

    @Bean
    ProjectorPolicy projectorPolicy(EventProjector eventProjector) {
        return new ProjectorPolicy(eventProjector);
    }
}