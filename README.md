# QuarkusGram

QuarkusGram is a Java-based backend application built with the Quarkus framework. The repository is designed to leverage reactive programming paradigms and provides features such as user management, secure password handling, and friendship functionalities. It utilizes modern technologies to ensure scalability, robustness, and developer productivity.

---

## Repository Overview

### **Domain**
QuarkusGram focuses on user-related operations. The core functionalities include:
- **User Management**: Create, retrieve, and manage user profiles.
- **Friendship Management**: Add friends and handle friendship requests between users.
- **Secure Password Handling**: Encrypt and match passwords securely using Jasypt.
- **Reactive Programming**: Implements reactive APIs and services for high performance.

---

## Technology Stack

| **Technology**       | **Purpose**                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| **Quarkus**           | Framework for building Java applications with reactive capabilities.       |
| **Java**              | Primary programming language (93.4%).                                      |
| **Kotlin**            | Secondary programming language (6.6%).                                     |
| **MongoDB with Panache** | Simplified database operations with MongoDB and repository patterns.     |
| **RESTEasy Reactive** | High-performance RESTful API implementation.                               |
| **Hibernate Validator** | Validates objects, fields, and method parameters for data integrity.      |
| **Mutiny**            | Provides reactive programming support.                                     |

---

## Project Structure

The repository follows a modular structure for separation of concerns:

### **Modules**
1. **`business`**: Contains core business logic, service interfaces, and domain models.
   - **Key Files/Features**:
     - `UserRepository`: Implements user persistence and interactions.
     - `PasswordMatcherGateway`: Interface for matching encrypted passwords.
   - **Dependencies**:
     - Jakarta Validation API
     - Hibernate Validator
     - Mutiny

2. **`application`**: Defines infrastructure, adapters, and configurations.
   - **Key Files/Features**:
     - `UserPanacheReactiveMongoRepository`: MongoDB-based persistence implementation.
     - `UserConfig`: Provides dependency injection for services and repositories.
     - `JasyptPasswordEncryptor`: Handles password encryption and matching.
   - **Dependencies**:
     - Jasypt for password encryption.
     - Quarkus Arc for dependency injection.

3. **`common`**: Shared utilities and configurations used across modules.

---

## Getting Started

### **Prerequisites**
- Java 11 or higher
- MongoDB instance (required for persistence)
- Gradle (build tool)

### **Running the Application**
To run the application in development mode:
```bash
./gradlew quarkusDev
```

### **Packaging and Running**
To build the project and create a runnable JAR:
```bash
./gradlew build
java -jar build/quarkus-app/quarkus-run.jar
```

### **Native Executable**
To build a native executable:
```bash
./gradlew build -Dquarkus.package.type=native
```
Run the executable:
```bash
./build/quarkusgram-0.0.1-runner
```

---

## Features

### **User Management**
- Create, retrieve, and manage user profiles.
- Handles secure password encryption and matching.

### **Friendship Management**
- Add friends and manage friendship requests.
- Reactive APIs for high-performance operations.

### **Reactive Programming**
- Built on Mutiny and RESTEasy Reactive for reactive APIs.
- Supports event-driven and asynchronous programming.

---

## Configuration

### **MongoDB Configuration**
The application uses MongoDB with Panache for persistence. Ensure your MongoDB instance is running and accessible.

### **Dependency Injection**
Quarkus Arc is used for dependency injection. Services and repositories are configured in the `UserConfig` class.

---

## Related Guides

- [MongoDB with Panache](https://quarkus.io/guides/mongodb-panache): Simplifies MongoDB database operations.
- [Hibernate Validator](https://quarkus.io/guides/validation): Ensures data integrity through validation.
- [RESTEasy Reactive](https://quarkus.io/guides/resteasy-reactive): Build reactive RESTful services.

---

## Contributing

We welcome contributions to QuarkusGram! To get started:
1. Fork the repository.
2. Create a feature branch.
3. Submit a Pull Request with a detailed explanation of changes.

For major changes, please open an issue first to discuss your ideas.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact

For questions or support, please open an issue in this repository.
