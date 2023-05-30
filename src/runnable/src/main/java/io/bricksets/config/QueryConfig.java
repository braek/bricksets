package io.bricksets.config;

import io.bricksets.api.brickset.ListBricksets;
import io.bricksets.query.brickset.BricksetViewModel;
import io.bricksets.query.brickset.ListBricksetsQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfig {

    @Bean
    ListBricksets listBricksets(BricksetViewModel bricksetViewModel) {
        return new ListBricksetsQuery(bricksetViewModel);
    }
}