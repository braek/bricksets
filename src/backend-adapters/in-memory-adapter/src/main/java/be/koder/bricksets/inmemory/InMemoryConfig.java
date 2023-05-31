package be.koder.bricksets.inmemory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "io.bricksets.rdbms.enabled", havingValue = "false")
public class InMemoryConfig {

    @Bean
    InMemoryBricksetRepository bricksetRepository() {
        return new InMemoryBricksetRepository();
    }

    @Bean
    InMemoryBricksetView bricksetView() {
        return new InMemoryBricksetView();
    }
}