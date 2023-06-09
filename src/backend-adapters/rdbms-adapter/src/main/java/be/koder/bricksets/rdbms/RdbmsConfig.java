package be.koder.bricksets.rdbms;

import be.koder.bricksets.rdbms.projector.RdbmsEventProjector;
import be.koder.bricksets.rdbms.repository.RdbmsBricksetRepository;
import be.koder.bricksets.rdbms.view.RdbmsBricksetView;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(name = "be.koder.bricksets.rdbms.enabled", havingValue = "true")
@EnableTransactionManagement
public class RdbmsConfig {

    @Bean
    DataSource dataSource(
            @Value("${be.koder.bricksets.rdbms.driver-class-name}") final String driverClassName,
            @Value("${be.koder.bricksets.rdbms.url}") final String url,
            @Value("${be.koder.bricksets.rdbms.username}") final String username,
            @Value("${be.koder.bricksets.rdbms.password}") final String password
    ) {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    DataSourceConnectionProvider connectionProvider(final DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    DSLContext dsl(final DataSourceConnectionProvider connectionProvider, final PlatformTransactionManager transactionManager) {
        final var config = new DefaultConfiguration();
        config.setSQLDialect(SQLDialect.POSTGRES);
        config.setTransactionProvider(new SpringTransactionProvider(transactionManager));
        config.set(connectionProvider);
        config.setSettings(new Settings().withExecuteWithOptimisticLocking(true).withExecuteWithOptimisticLockingExcludeUnversioned(true));
        return new DefaultDSLContext(config);
    }

    @Bean
    RdbmsBricksetRepository bricksetRepository(final DSLContext dsl) {
        return new RdbmsBricksetRepository(dsl);
    }

    @Bean
    RdbmsEventProjector eventProjector(final DSLContext dsl) {
        return new RdbmsEventProjector(dsl);
    }

    @Bean
    RdbmsBricksetView bricksetViewModel(final DSLContext dsl) {
        return new RdbmsBricksetView(dsl);
    }
}