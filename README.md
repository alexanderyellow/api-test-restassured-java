# API Test Automation Framework

API test framework for the Slotegrator test API built with Java, RestAssured, JUnit 5, and Allure.

The project uses an actor-style approach:
- `Actor` represents a user/session (authenticated or not).
- `HttpAction` encapsulates one HTTP operation against a predefined endpoint.

## Tech Stack

- Java 21
- Gradle
- RestAssured
- JUnit 5
- AssertJ
- Allure Report
- Datafaker
- SmallRye Config (YAML + env vars + system properties)

## Project Structure

```text
src/main/java/org/example/
├── actions/        # endpoint registry and generic HTTP action
├── actors/         # actors that execute actions and keep auth session
├── clients/        # RestAssured client/spec setup and auth header injection
├── config/         # configuration mapping/bootstrap
├── model/          # request/response DTOs
└── utils/          # helpers (for example request/response log masking)

src/test/java/org/example/
├── *Test.java                  # functional API tests
├── permissions/*PermissionsTest.java
└── data/PlayerTestDataFactory.java
```

## Configuration

Default config file is `src/main/resources/application.yml`.

### Configure via environment variables

```bash
export AUTH_EMAIL="your-email@example.com"
export AUTH_PASSWORD="your-password"
```

Optional:

```bash
export API_BASE_URL="https://example.com/api"
export LOGGING_ENABLED="true"
```

### Configure via JVM properties

```bash
./gradlew test \
  -Dauth.email="your-email@example.com" \
  -Dauth.password="your-password" \
  -Dapi.base-url="https://example.com/api" \
  -Dlogging.enabled=true
```

## Run Tests

Run full suite:

```bash
./gradlew clean test
```

Run one class:

```bash
./gradlew test --tests "org.example.CreatePlayerTest"
```

Run one package:

```bash
./gradlew test --tests "org.example.permissions.*"
```

## Allure Report

Generate report:

```bash
./gradlew clean test allureReport
```

Open local interactive report:

```bash
./gradlew allureServe
```

## Parallel Execution

Parallel mode is configured in `build.gradle`:
- class-level parallel execution is enabled
- fixed parallelism is set to `5`
- method-level default is `same_thread` unless overridden with JUnit annotations (for example `@Execution(ExecutionMode.CONCURRENT)`)
