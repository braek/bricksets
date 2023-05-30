package io.bricksets.rdbms;

import io.bricksets.domain.time.TimeService;
import io.bricksets.rdbms.repository.brickset.RdbmsBricksetRepository;
import io.bricksets.test.MockTimeService;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestConfig {

    @Bean
    DSLContext dsl(DataSource dataSource) {
        return new DefaultDSLContext(
                dataSource,
                SQLDialect.POSTGRES,
                new Settings().withRenderMapping(
                        new RenderMapping().withSchemata(
                                new MappedSchema()
                                        .withInput(Sandbox.SANDBOX.getName())
                        )
                ));
    }

    @Bean
    RdbmsBricksetRepository bricksetRepository(DSLContext dsl) {
        return new RdbmsBricksetRepository(dsl);
    }

    @Bean
    TimeService timeService() {
        return new MockTimeService();
    }
}