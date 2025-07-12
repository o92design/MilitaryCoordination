# Contributing to Military Coordination System

Thank you for your interest in contributing to the Military Coordination System! This document provides guidelines for contributing to the project.

## Getting Started

1. Fork the repository on GitHub
2. Clone your fork locally
3. Set up the development environment (see README.md)
4. Create a new branch for your changes

## Development Environment

### Prerequisites

- Java 21 or higher
- Maven 3.8+
- Git
- An IDE (IntelliJ IDEA recommended)

### Setup

```bash
git clone https://github.com/YOUR_USERNAME/MilitaryCoordination.git
cd MilitaryCoordination
mvn clean compile
mvn test
```

## Making Changes

### Branch Naming

Use descriptive branch names:

- `feature/unit-movement-system` - New features
- `fix/memory-leak-in-game-loop` - Bug fixes
- `docs/api-documentation` - Documentation updates
- `refactor/data-model-cleanup` - Code refactoring

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Write self-documenting code with appropriate comments
- Keep methods focused and small (ideally under 20 lines)
- Follow functional programming principles where applicable

### Testing

- Write unit tests for all new functionality
- Ensure all existing tests pass
- Aim for high test coverage (minimum 80%)
- Use descriptive test method names

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn clean test jacoco:report
```

### Code Quality

Before submitting your changes, ensure they pass all quality checks:

```bash
# Run code style checks
mvn checkstyle:check

# Run static analysis
mvn spotbugs:check

# Run all quality checks
mvn clean test checkstyle:check spotbugs:check
```

## Submitting Changes

### Pull Request Process

1. Update your branch with the latest changes from main:

   ```bash
   git checkout main
   git pull upstream main
   git checkout your-feature-branch
   git rebase main
   ```

2. Run all tests and quality checks
3. Commit your changes with a clear message
4. Push to your fork
5. Create a Pull Request

### Commit Messages

Use clear, descriptive commit messages:

```text
feat: add unit movement validation system

- Implement boundary checking for unit positions
- Add tests for edge cases
- Update documentation for movement API

Fixes #123
```

Format:

- `feat:` - New features
- `fix:` - Bug fixes
- `docs:` - Documentation changes
- `refactor:` - Code refactoring
- `test:` - Adding or updating tests
- `chore:` - Maintenance tasks

### Pull Request Guidelines

- Provide a clear description of the changes
- Reference any related issues
- Include screenshots or examples if applicable
- Ensure CI checks pass
- Request review from maintainers

## Code Review Process

1. All submissions require review before merging
2. Reviewers will check for:
   - Code quality and style
   - Test coverage
   - Performance implications
   - Adherence to project goals
3. Address feedback promptly
4. Maintain a respectful tone in discussions

## Reporting Issues

### Bug Reports

When reporting bugs, include:

- Clear description of the issue
- Steps to reproduce
- Expected vs actual behavior
- Environment details (OS, Java version, etc.)
- Relevant logs or error messages

### Feature Requests

For new features:

- Describe the problem you're trying to solve
- Explain why this feature would be valuable
- Provide examples or mockups if applicable
- Consider the impact on existing functionality

## Development Guidelines

### Data-Oriented Design

- Focus on data structures and transformations
- Minimize object-oriented abstractions
- Optimize for cache efficiency
- Use immutable data where possible

### Functional Programming

- Prefer pure functions
- Use streams and lambdas effectively
- Avoid side effects in business logic
- Leverage Java's functional interfaces

### Performance

- Profile code changes for performance impact
- Use appropriate data structures
- Consider memory allocation patterns
- Write benchmarks for critical paths

## Documentation

- Update relevant documentation for changes
- Include inline code comments for complex logic
- Update API documentation (Javadoc)
- Add examples for new features

## Community

### Code of Conduct

- Be respectful and inclusive
- Focus on what's best for the project
- Show empathy towards other contributors
- Accept constructive criticism gracefully

### Getting Help

- Check existing issues and documentation first
- Ask questions in GitHub Discussions
- Provide context when asking for help
- Share knowledge with other contributors

## Recognition

Contributors will be recognized in:

- README.md acknowledgments
- Release notes for significant contributions
- Project documentation

Thank you for contributing to the Military Coordination System!
