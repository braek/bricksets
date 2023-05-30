package io.bricksets.config;

import io.bricksets.api.brickset.ListBricksets;
import io.bricksets.query.brickset.BricksetView;
import io.bricksets.query.brickset.ListBricksetsQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfig {

    @Bean
    ListBricksets listBricksets(BricksetView bricksetView) {
        return new ListBricksetsQuery(bricksetView);
    }
}