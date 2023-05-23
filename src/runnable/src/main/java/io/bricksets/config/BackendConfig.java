package io.bricksets.config;

import io.bricksets.domain.time.TimeService;
import io.bricksets.inmemory.InMemoryBricksetRepository;
import io.bricksets.inmemory.InMemoryEventPublisher;
import io.bricksets.system.SystemTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendConfig {

    @Bean
    InMemoryBricksetRepository bricksetRepository() {
        return new InMemoryBricksetRepository();
    }

    @Bean
    InMemoryEventPublisher eventPublisher() {
        return new InMemoryEventPublisher();
    }

    @Bean
    TimeService timeService() {
        return new SystemTimeService();
    }
}