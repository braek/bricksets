package be.koder.bricksets.config;

import be.koder.bricksets.domain.event.EventProjector;
import be.koder.bricksets.policy.ProjectorPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PolicyConfig {

    @Bean
    ProjectorPolicy projectorPolicy(EventProjector eventProjector) {
        return new ProjectorPolicy(eventProjector);
    }
}
