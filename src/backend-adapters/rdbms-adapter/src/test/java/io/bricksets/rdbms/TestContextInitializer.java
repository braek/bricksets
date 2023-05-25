package io.bricksets.rdbms;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public final class TestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of(
                "spring.datasource.url=jdbc:postgresql://localhost:6000/sandbox",
                "spring.datasource.username=sandbox",
                "spring.datasource.password=sandbox",
                "spring.datasource.driver-class-name=org.postgresql.Driver",
                "spring.jpa.hibernate.ddl-auto=none"
        ).applyTo(context.getEnvironment());
    }
}