# Event Sourcing POC (proof of concept)

## Running the project

First, spin up the Docker containers:

```docker compose up```

Second, run this Gradle task:

```./gradlew flywayMigrate```

Finally, run the Spring Boot application through a ```Run Configuration``` in IntelliJ with ```be.koder.bricksets.App``` as **main class.**

## Resources

* [Kill Aggregate! - Sara Pellegrini](https://www.youtube.com/watch?v=DhhxKoOpJe0)
* [Event Sourcing - Martin Fowler](https://martinfowler.com/eaaDev/EventSourcing.html)