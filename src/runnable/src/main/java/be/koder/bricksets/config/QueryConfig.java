package be.koder.bricksets.config;

import be.koder.bricksets.api.brickset.ListBricksets;
import be.koder.bricksets.query.brickset.BricksetView;
import be.koder.bricksets.query.brickset.ListBricksetsQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfig {

    @Bean
    ListBricksets listBricksets(BricksetView bricksetView) {
        return new ListBricksetsQuery(bricksetView);
    }
}