# Development Workflow Guide

## 🎯 Issue-Based Development Workflow

This project follows a structured development workflow based on GitHub issues and feature branches.

## 📋 Current Epic Structure

We have organized our development into **6 Epics** with corresponding GitHub issues:

- **Epic 1**: Project Setup (Complete ✅)
- **Epic 2**: Core Data Models (#5) - **Current Focus**
  - Position/Coordinate System (#8)
  - Unit Data Structure (#9)
  - Resource Management (#10)
  - Game State Management (#11)
- **Epic 3**: Game Loop (#6)
- **Epic 4**: Unit Coordination (#7)
- **Epic 5**: Resources (#12)
- **Epic 6**: Advanced Features (#13)

## 🔄 Development Workflow

### 1. Pick an Issue
1. **Review Epic 2 issues** (#8, #9, #10, #11)
2. **Choose the next unassigned issue**
3. **Assign yourself** to the issue
4. **Move to "In Progress"** on the project board

### 2. Create Feature Branch
```bash
# Update main branch
git checkout main
git pull origin main

# Create feature branch (example for issue #8)
git checkout -b feature/issue-8-position-coordinate-system

# Alternative naming for different issue types:
git checkout -b feature/issue-9-unit-data-structure
git checkout -b fix/issue-15-memory-leak
git checkout -b refactor/issue-20-cleanup-models
```

### 3. Development Process

1. **Implement the feature**
   - Follow the acceptance criteria in the GitHub issue
   - Write clean, well-documented code
   - Follow existing code patterns

2. **Write tests when you have a clear case of what to test in order to stabilize the product**

   ```bash
   mvn test
   ```

3. **Validate your work**

   ```bash
   # Run tests
   mvn clean test

   # Run code quality checks
   mvn checkstyle:check spotbugs:check

   # Run the application
   mvn clean compile exec:java
   ```

### 4. Commit and Push
```bash
# Stage changes
git add .

# Commit with descriptive message
git commit -m "feat: implement position coordinate system

- Add Position class with x,y coordinates
- Add validation for boundary checking
- Include comprehensive unit tests
- Update documentation

Implements #8"

# Push to your feature branch
git push origin feature/issue-8-position-coordinate-system
```

### 5. Create Pull Request
1. **Go to GitHub** and create a Pull Request
2. **Title**: Use issue title - "feat: Position/Coordinate System #8"
3. **Description**:
   ```markdown
   ## Summary
   Implements the Position/Coordinate System as defined in Epic 2.

   ## Changes
   - [x] Added Position class with x,y coordinates
   - [x] Implemented boundary validation
   - [x] Added comprehensive unit tests
   - [x] Updated documentation

   ## Testing
   - All tests pass: `mvn test`
   - Code quality checks pass: `mvn checkstyle:check`
   - Manual testing completed

   Closes #8
   ```

4. **Request review** and wait for approval
5. **Merge** when approved (use squash and merge)

### 6. Post-Merge Cleanup
```bash
# Switch back to main
git checkout main

# Pull the merged changes
git pull origin main

# Delete the feature branch
git branch -d feature/issue-8-position-coordinate-system
git push origin --delete feature/issue-8-position-coordinate-system
```

## 🏃‍♂️ Quick Start for Epic 2

### Ready to start? Pick your first issue:

**Option 1: Position/Coordinate System (#8)**
```bash
git checkout -b feature/issue-8-position-coordinate-system
```

**Option 2: Unit Data Structure (#9)**
```bash
git checkout -b feature/issue-9-unit-data-structure
```

**Option 3: Resource Management (#10)**
```bash
git checkout -b feature/issue-10-resource-management
```

## 📝 Branch Naming Conventions

| Type | Pattern | Example |
|------|---------|---------|
| Feature | `feature/issue-{number}-{short-description}` | `feature/issue-8-position-system` |
| Bug Fix | `fix/issue-{number}-{short-description}` | `fix/issue-15-memory-leak` |
| Refactor | `refactor/issue-{number}-{short-description}` | `refactor/issue-20-cleanup-models` |
| Documentation | `docs/issue-{number}-{short-description}` | `docs/issue-25-api-documentation` |

## 🔍 Code Quality Standards

Before creating a Pull Request, ensure:

- ✅ **All tests pass**: `mvn test`
- ✅ **Code style compliance**: `mvn checkstyle:check`
- ✅ **No bugs detected**: `mvn spotbugs:check`
- ✅ **Application runs**: `mvn clean compile exec:java`
- ✅ **Documentation updated** (if applicable)

## 🚀 VS Code Integration

### Quick Commands:
- **F5**: Run application with debugging
- **Ctrl+Shift+P → Tasks**: Run Maven tasks
- **Ctrl+Shift+` **: Open terminal for git commands

### Recommended Extensions:
- GitLens - Enhanced Git capabilities
- Java Extension Pack - Full Java development support
- Test Runner for Java - Run tests in IDE

## 🎯 Current Priority

**Start with Epic 2 - Core Data Models**
1. Position/Coordinate System (#8) - Foundation for all movement
2. Unit Data Structure (#9) - Core game entities
3. Resource Management (#10) - Game economy
4. Game State Management (#11) - System coordination

Pick an issue, create a branch, and start coding! 🚀
