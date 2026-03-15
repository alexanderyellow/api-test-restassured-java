# API Test Automation Framework with RestAssured & Java

This project is a professional API test automation framework built using **Java**, **RestAssured** and **JUnit 5**. It is designed to test RESTful services with a focus on maintainability, scalability, and clear reporting.

## 🚀 Tech Stack

- **Language:** Java 21
- **Build Tool:** Gradle
- **API Client:** [RestAssured](https://rest-assured.io/)
- **Test Runner:** [JUnit 5](https://junit.org/junit5/)
- **Assertions:** [AssertJ](https://assertj.github.io/doc/)
- **Reporting:** [Allure Report](https://qameta.io/allure-report/)
- **Logging:** Log4j2
- **Data Generation:** [Datafaker](https://www.datafaker.net/)
- **Serialization:** Jackson

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/org/example/
│   │   ├── actions/        # Encapsulated API operations (Create, Delete, Get, etc.)
│   │   ├── actors/         # Represents a user/actor performing actions
│   │   ├── clients/        # API client configuration (RestAssured specs, filters)
│   │   ├── config/         # Configuration management (YAML based)
│   │   ├── model/          # DTOs for requests and responses
│   │   └── utils/          # Helper utilities
│   └── resources/          # Configuration files and logging setup
└── test/
    ├── java/org/example/
    │   ├── BaseTest.java   # Common test setup and teardown
    │   ├── DemoTest.java   # Example test suite
    │   └── data/           # Test data factories
```

## 🛠️ Key Architectural Patterns

- **Actor-Action Pattern**: Promotes clean and reusable code by separating user roles (Actors) from their capabilities (Actions).
- **Declarative API Actions**: Simplifies test creation by encapsulating request/response logic into reusable action classes.
- **Dynamic Test Data**: Uses Datafaker to generate realistic and unique data for each test run.
- **Rich Reporting**: Integrates with the Allure Framework to produce detailed and interactive test reports.
- **Flexible Configuration**: Easily manage environment-specific settings using YAML, with overrides via system properties.

## ⚙️ Prerequisites

- **Java 21** (JDK 21)
- **Gradle** (using the provided wrapper `./gradlew`)

## 🏃 Getting Started

### 1. Configuration
The default configuration is in `src/main/resources/application-config.yml`. You can override values using environment variables:
- `USER_EMAIL`
- `USER_PASSWORD`

### 2. Running Tests
To run all tests:
```bash
./gradlew clean test
```

To run tests with logging disabled:
```bash
./gradlew clean test -Dlogging=false
```

### 3. Generating Reports
After running tests, generate and open the Allure report:
```bash
./gradlew allureReport
./gradlew allureServe
```

## 📊 Reporting
The framework is fully integrated with **Allure**. Each test execution produces detailed results including:
- HTTP Request/Response details (with masked sensitive info).
- Steps and sub-steps.
- Detailed failure reasons with stack traces.

## 🔗 CI/CD Integration
This project includes a **GitHub Actions** workflow (`.github/workflows/api-test.yml`) that:
1. Runs tests on every push/pull request.
2. Generates and uploads Allure reports as artifacts.
3. Uses secrets for sensitive credentials.
