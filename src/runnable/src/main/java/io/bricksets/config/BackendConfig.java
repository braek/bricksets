package io.bricksets.config;

import io.bricksets.domain.time.TimeService;
import io.bricksets.inmemory.InMemoryEventPublisher;
import io.bricksets.policy.ProjectorPolicy;
import io.bricksets.system.SystemTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendConfig {

    @Bean
    InMemoryEventPublisher eventPublisher(ProjectorPolicy projectorPolicy) {
        final var eventPublisher = new InMemoryEventPublisher();
        eventPublisher.addSubscriber(projectorPolicy);
        return eventPublisher;
    }

    @Bean
    TimeService timeService() {
        return new SystemTimeService();
    }
}