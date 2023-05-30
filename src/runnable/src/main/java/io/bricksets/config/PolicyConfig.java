package io.bricksets.config;

import io.bricksets.domain.event.EventProjector;
import io.bricksets.policy.ProjectorPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PolicyConfig {

    @Bean
    ProjectorPolicy projectorPolicy(EventProjector eventProjector) {
        return new ProjectorPolicy(eventProjector);
    }
}
