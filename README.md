# Military Coordination System

[![CI - Build and Test](https://github.com/o92design/MilitaryCoordination/actions/workflows/ci.yml/badge.svg)](https://github.com/o92design/MilitaryCoordination/actions/workflows/ci.yml)
[![Security and Dependencies](https://github.com/o92design/MilitaryCoordination/actions/workflows/security.yml/badge.svg)](https://github.com/o92design/MilitaryCoordination/actions/workflows/security.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-21%2B-orange.svg)](https://openjdk.org/projects/jdk/21/)

A single-player real-time military coordination system built in Java using data-oriented functional programming principles. This project demonstrates modern Java development practices, functional programming concepts, and real-time system design.

## 🎯 Project Overview

The Military Coordination System is a strategic simulation that manages military units, resources, and operations in real-time. It serves as a practical application for learning advanced Java programming techniques, including:

- **Data-Oriented Design**: Efficient data structures and transformations
- **Functional Programming**: Pure functions, immutable data, and streams
- **Real-Time Processing**: Continuous game loop with state management
- **Strategic Coordination**: Complex unit management and decision-making

## ✨ Features

### Current Features

- ✅ Enterprise-grade project setup with Maven
- ✅ Comprehensive CI/CD pipeline with GitHub Actions
- ✅ Automated security scanning and dependency management
- ✅ Code quality tools (Checkstyle, SpotBugs, JaCoCo)
- ✅ Documentation generation and performance monitoring

### Planned Features

- 🔄 Core data models (Unit, Command, World State)
- 🔄 Real-time game loop implementation
- 🔄 Unit coordination and command processing
- 🔄 Resource management system
- 🔄 Strategic AI for autonomous units
- 🔄 Performance optimization and monitoring

## 🚀 Quick Start

### Prerequisites

- **Java 21 or higher** - [Download OpenJDK](https://openjdk.org/projects/jdk/21/)
- **Maven 3.8+** - [Installation Guide](https://maven.apache.org/install.html)
- **Git** - [Download Git](https://git-scm.com/downloads)

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/o92design/MilitaryCoordination.git
   cd MilitaryCoordination
   ```

2. **Build the project**

   ```bash
   mvn clean compile
   ```

3. **Run tests**

   ```bash
   mvn test
   ```

4. **Generate documentation**

   ```bash
   mvn javadoc:javadoc
   ```

### Development Setup

For development with IDE support:

1. **IntelliJ IDEA** (Recommended)
   - Open the project folder
   - IntelliJ will automatically detect the Maven configuration
   - Use the provided `.idea` configurations for optimal settings

2. **Visual Studio Code**
   - Install Java Extension Pack
   - Open the project folder
   - Use provided tasks in `.vscode/tasks.json`

## 🔄 Development Workflow

**Ready to contribute?** Follow our issue-based development workflow:

1. **📋 [Development Workflow Guide](DEVELOPMENT_WORKFLOW.md)** - Complete guide for branch-based development
2. **🤝 [Contributing Guidelines](.github/CONTRIBUTING.md)** - Code style, testing, and PR process
3. **⚙️ [Development Setup](DEVELOPMENT.md)** - Maven, VS Code, and build configuration

### Quick Start for New Contributors:
```bash
# Pick an issue from Epic 2 (Issues #8, #9, #10, #11)
git checkout -b feature/issue-8-position-coordinate-system

# Develop using TDD
mvn test  # Write failing tests first
# Implement feature
mvn clean test checkstyle:check  # Validate your work

# Create PR when ready
git push origin feature/issue-8-position-coordinate-system
```

**Current Focus**: Epic 2 - Core Data Models (Issues #8-#11)

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Language** | Java | 21+ |
| **Build Tool** | Apache Maven | 3.8+ |
| **Testing** | JUnit 5 | 5.10+ |
| **Code Quality** | Checkstyle, SpotBugs | Latest |
| **Coverage** | JaCoCo | Latest |
| **Security** | OWASP Dependency Check | Latest |
| **CI/CD** | GitHub Actions | - |
| **Documentation** | Javadoc | - |

## 📋 Development Phases

### Phase 1: Foundation ✅

- [x] Project setup and Maven configuration
- [x] CI/CD pipeline with GitHub Actions
- [x] Code quality and security scanning
- [x] Documentation infrastructure

### Phase 2: Core Data Models 🔄

- [ ] Core data structures (Unit, Command, WorldState)
- [ ] Immutable data patterns
- [ ] Functional transformations
- [ ] Basic validation and serialization

### Phase 3: Game Loop System 📅

- [ ] Real-time processing engine
- [ ] Event-driven architecture
- [ ] State management
- [ ] Performance monitoring

### Phase 4: Military Coordination 📅

- [ ] Unit command processing
- [ ] Resource management
- [ ] Strategic AI algorithms
- [ ] Complex scenario handling

### Phase 5: Optimization & Polish 📅

- [ ] Performance tuning
- [ ] Memory optimization
- [ ] Enhanced user interface
- [ ] Save/load functionality

## 🏗️ Architecture Overview

```text
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Data Models   │    │   Game Engine   │    │   Coordination  │
│                 │    │                 │    │                 │
│ • Unit          │◄──►│ • Game Loop     │◄──►│ • Command Proc  │
│ • Command       │    │ • State Mgmt    │    │ • Resource Mgmt │
│ • WorldState    │    │ • Event System  │    │ • Strategic AI  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Key Principles

- **Data-Oriented Design**: Focus on data layout and transformations
- **Functional Approach**: Emphasize pure functions and immutability
- **Performance First**: Optimize for real-time processing requirements
- **Maintainable Code**: Clear structure and comprehensive testing

## 🧪 Testing

The project uses a comprehensive testing strategy:

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn clean test jacoco:report

# Run only unit tests
mvn test -Dtest="**/*Test"

# Run integration tests (when available)
mvn test -Dtest="**/*IT"
```

## 📊 Code Quality

Automated code quality checks include:

- **Checkstyle**: Code style and formatting
- **SpotBugs**: Static analysis for potential bugs
- **JaCoCo**: Test coverage reporting
- **OWASP**: Security vulnerability scanning

```bash
# Run code quality checks
mvn checkstyle:check spotbugs:check

# Generate quality reports
mvn site
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](.github/CONTRIBUTING.md) for details.

### Development Workflow

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Run tests and quality checks (`mvn clean test checkstyle:check`)
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

### Code Standards

- Follow Java naming conventions
- Write comprehensive tests for new features
- Update documentation for public APIs
- Ensure all CI checks pass

## 📈 Performance

The system is designed for high-performance real-time processing:

- **Target**: 60 FPS game loop (16.67ms per frame)
- **Memory**: Efficient data structures with minimal allocations
- **Concurrency**: Thread-safe operations where needed
- **Monitoring**: Built-in performance metrics and profiling

## 🔒 Security

Security is a priority with automated scanning and best practices:

- **Dependency Scanning**: Automated vulnerability detection
- **Code Analysis**: Static security analysis with CodeQL
- **License Compliance**: Automated license checking
- **Supply Chain**: Verified dependencies and reproducible builds

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors & Contact

- **Developer**: [@o92design](https://github.com/o92design)
- **Project**: [Military Coordination System](https://github.com/o92design/MilitaryCoordination)

## 🙏 Acknowledgments

- Java community for excellent functional programming resources
- Maven ecosystem for build and dependency management
- GitHub Actions for seamless CI/CD integration
- Open source security tools for keeping the project safe

## 📚 Additional Resources

- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
- [Functional Programming in Java](https://www.oracle.com/technical-resources/articles/java/architect-lambdas-part1.html)
- [Data-Oriented Design](https://www.dataorienteddesign.com/dodbook/)
- [Real-Time Systems Design](https://en.wikipedia.org/wiki/Real-time_computing)

---

**Status**: 🚧 Active Development | **Version**: 1.0-SNAPSHOT | **Last Updated**: July 2025
