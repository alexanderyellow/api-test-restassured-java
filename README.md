# API Test Automation Framework (RestAssured + TestNG)

This project is a robust API test automation framework built using **Java**, **RestAssured**, and **TestNG**. It is designed to test RESTful services with a focus on maintainability, scalability, and clear reporting.

## ✨ Key Features

- **Parallel Test Execution**: Leverages TestNG to run tests in parallel, significantly reducing execution time.
- **Actor-Action Pattern**: Promotes clean and reusable code by separating user roles (Actors) from their capabilities (Actions).
- **Declarative API Actions**: Simplifies test creation by encapsulating request/response logic into reusable action classes.
- **Dynamic Test Data**: Uses Datafaker to generate realistic and unique data for each test run.
- **Rich Reporting**: Integrates with the Allure Framework to produce detailed and interactive test reports.
- **Flexible Configuration**: Easily manage environment-specific settings using YAML, with overrides via system properties.

## 🚀 Technology Stack

- **Language**: Java 21+
- **Build Tool**: Gradle
- **Testing Framework**: TestNG (for parallel execution and assertions)
- **API Client**: RestAssured (for HTTP requests and responses)
- **Reporting**: Allure Framework
- **Data Generation**: Datafaker (for realistic test data)
- **Logging**: Log4j2
- **Configuration**: YAML-based (SnakeYAML)

## ✅ Prerequisites

- **JDK 21** or higher.
- **Gradle** is used for dependency management and running tasks (the repository includes a Gradle Wrapper, so no installation is needed).

## 🏗 Project Architecture

The framework follows an **Actor-Action** pattern to separate concerns and improve readability:

- **Actors (`src/main/java/org/example/actors`)**: Represent different user roles (e.g., `Admin`, `Supervisor`, `User`).
- **Actions (`src/main/java/org/example/actions`)**: Encapsulate atomic API operations (e.g., `CreatePlayerAction`, `DeletePlayerAction`). Each action is responsible for a single request.
- **Clients (`src/main/java/org/example/clients`)**: Base API client and session management using RestAssured.
- **Models (`src/main/java/org/example/model`)**: POJOs for request/response serialization and deserialization.
- **Data Factories (`src/test/java/org/example/data`)**: Utilities to generate random test data using Datafaker.

## 🛠 Configuration

Configuration is managed via `src/main/resources/application-config.yml`. Values can be overridden using System Properties or Environment Variables:

- `baseUrl`: Base URL of the API.
- `supervisorLogin`: Default supervisor login.
- `adminLogin`: Default admin login.

Example override:
```bash
./gradlew test -DbaseUrl=https://api.staging.example.com
```

## 🏃 Running Tests

The project uses the Gradle wrapper (`./gradlew`) to ensure a consistent build environment.

### Basic Test Execution
To compile the code and run all tests:
```bash
./gradlew clean test
```

### Advanced Options
You can customize the test run with the following properties:

- **Run in Parallel (with a specific thread count):**
  ```bash
  ./gradlew clean test -DthreadCount=5
  ```
- **Show Console Logs:**
  ```bash
  ./gradlew test -DshowStandardStreams=true
  ```

## 📊 Reporting

The project uses **Allure** for detailed test execution reports.

1. **Generate and open the report immediately:**
   ```bash
   ./gradlew allureServe
   ```

2. **Generate the static report:**
   ```bash
   ./gradlew allureReport
   ```
   The report will be located in `build/reports/allure-report`.
