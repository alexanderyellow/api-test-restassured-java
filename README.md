# API Test Automation Framework (RestAssured + Actor-Action Pattern)

A modern, highly scalable API test automation framework built with **Java 21**, **RestAssured**, and **JUnit 5**. This project implements the **Actor-Action Pattern**, providing a clean separation between "who" performs an operation and "what" that operation is.

## 🚀 Tech Stack

- **Core:** Java 21, Gradle
- **API Client:** [RestAssured](https://rest-assured.io/)
- **Test Runner:** [JUnit 5](https://junit.org/junit5/) (Parallel Execution enabled)
- **Assertions:** [AssertJ](https://assertj.github.io/doc/) (Fluent assertions & Soft Assertions)
- **Reporting:** [Allure Report](https://qameta.io/allure-report/)
- **Data Generation:** [Datafaker](https://www.datafaker.net/)
- **Serialization:** Jackson (JSON)
- **Configuration:** SmallRye Config (YAML + Environment Variables + System Properties)

## 📁 Project Architecture

The framework follows a modular structure to ensure maintainability:

```text
src/main/java/org/example/
├── actions/        # Atomic API operations (Encapsulated Request/Response logic)
├── actors/         # Actors that hold state (Session/Tokens) and perform Actions
├── clients/        # Base API Client, Request/Response Specs, and Filters
├── config/         # SmallRye Config bootstrap and access layer
├── model/          # DTOs (Data Transfer Objects) for API Payloads
└── utils/          # Custom Filters (e.g., Sensitive Data Masking)
```

## 🏗️ Key Design Patterns

### 1. Actor-Action Pattern
Unlike traditional approaches, this framework uses **Actors**:
- **Actor**: Represents a user or system entity. It maintains state (like Authentication Tokens) and provides a `perform()` method.
- **Action**: Represents a single API interaction (e.g., `CreatePlayerAction`). It encapsulates the endpoint, method, headers, and body.

**Example Usage in Tests:**
```java
PlayerResponseDTO response = admin.perform(apiClient -> 
    new CreatePlayerAction(apiClient, playerRequestDTO)
).as(PlayerResponseDTO.class);
```

### 2. Fluent Action Configuration
Actions can be configured fluently, allowing for custom status code validation or parameter overrides:
```java
admin.perform(apiClient -> 
    new CreatePlayerAction(apiClient, duplicateRequest)
        .withExpectedStatusCode(400)
);
```

### 3. Data-Driven Testing
Integration with **Datafaker** and JUnit 5 **Parameterized Tests** allows for robust load and boundary testing:
- `PlayerTestDataFactory` generates unique, valid, and invalid data sets dynamically.

## 🏃 Getting Started

### Prerequisites
- **JDK 21**
- **Gradle** (included wrapper)

### Environment Configuration
The framework uses `src/main/resources/application.yml` as the default config file. Override values with standard SmallRye configuration sources:
- Environment Variables: `AUTH_EMAIL`, `AUTH_PASSWORD`, `API_BASE_URL`, `LOGGING_ENABLED`
- System Properties: `-Dauth.email=...`, `-Dauth.password=...`, `-Dapi.base-url=...`, `-Dlogging.enabled=false`
- Optional profile: `SMALLRYE_CONFIG_PROFILE=local`
- Optional extra config file: `SMALLRYE_CONFIG_LOCATIONS=file:/absolute/path/to/application.yml`

### Execution Commands

| Task | Command |
| :--- | :--- |
| **Run All Tests** | `./gradlew clean test` |
| **Run with Allure** | `./gradlew clean test allureReport` |
| **Open Report** | `./gradlew allureServe` |
| **Silent Logging** | `./gradlew test -Dlogging.enabled=false` |

## 📊 Parallel Execution
Parallel execution is enabled by default in `build.gradle` using a fixed strategy (default: 5 threads). Individual test classes or methods can further control concurrency using JUnit 5 annotations:
```java
@Execution(ExecutionMode.CONCURRENT)
```

## ☁️ CI/CD Integration
Automated testing is integrated via **GitHub Actions** (`.github/workflows/api-test.yml`). It:
1. Executes tests on JDK 21.
2. Uses GitHub Secrets for credentials.
3. Publishes Allure Reports as build artifacts for 20 days.
