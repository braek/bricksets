package be.koder.bricksets.config;

import be.koder.bricksets.domain.time.TimeService;
import be.koder.bricksets.inmemory.InMemoryEventPubSub;
import be.koder.bricksets.policy.ProjectorPolicy;
import be.koder.bricksets.system.SystemTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendConfig {

    @Bean
    InMemoryEventPubSub eventPubSub(final ProjectorPolicy projectorPolicy) {
        final var eventPubSub = new InMemoryEventPubSub();
        eventPubSub.subscribe(projectorPolicy);
        return eventPubSub;
    }

    @Bean
    TimeService timeService() {
        return new SystemTimeService();
    }
}