package io.bricksets.rdbms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApp {
    public static void main(final String[] args) {
        final SpringApplication app = new SpringApplication(TestApp.class);
        app.run(args);
    }
}
