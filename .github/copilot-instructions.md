# Military Coordination System - AI Agent Instructions

This is a **tactical command simulation game** implementing a **5-second tick Tower Trust System™** using data-oriented functional programming principles in Java 21+.

## Core Game Design Context

You are the **Commander** issuing commands through **SignalTowers** to **Unit Leads** who manage field units. The game rewards strategic awareness over reflexes: *"how clearly can you think?"*

**Core Loop**: Issue Command → Relay → Delegate → Execute → Report → Assess

Key mechanics:
- **Adaptive Command Economy (ACE)**: Command costs vary by unit trust, stress, and signal strength
- **5-second command ticks** (see `ConsoleGame.java` and `MilitaryCoordinationGame.java`)
- **Tower Trust System™**: Units prefer different tower types (Static, Field, Civilian, Drone)
- **Signal Zones**: Green/Yellow/Red with varying integrity and delay
- **Command Queue**: Limited bandwidth (3 concurrent), priority-based processing

## Architectural Patterns

### Data-Oriented Functional Programming
- **Pure Functions**: All `*System.java` classes contain only pure functions (see `CommandSystem.java`, `GridUtilities.java`)
- **Immutable Data**: Records and immutable classes with `withX()` methods (see `Command.withStatus()`)
- **Data Transformation Pipelines**: Functions compose data flows, no side effects
- **ECS-Style**: Entities as data, systems as pure transformation functions

### Multi-Module Maven Structure
```
parent/
├── core/           # Game logic, models, systems (pure Java + LibGDX)
└── desktop/        # Desktop launcher (depends on core)
```

## Critical Development Patterns

### 1. Command System Architecture
Commands are **immutable records** with functional transformations:
```java
// Create with withStatus() - never mutate originals
var executing = command.withStatus(CommandStatus.EXECUTING);
```

Cost calculation uses pure functions in `CommandSystem.calculateCost()` based on:
- Unit trust (0-100): Higher trust = lower cost
- Unit stress: Accumulated from back-to-back orders
- Signal strength: Tower network integrity

### 2. Grid System (ECS Pattern)
- `GridCoordinate` + `GridActorMap` for spatial data
- `GridUtilities` provides pure transformation functions
- All coordinate operations are **pure functions** returning new state

### 3. Game Loop Implementation
- **Real-time 60 FPS** with **5-second command ticks**
- State updates are **immutable transformations**
- Background processing in `MilitaryCoordinationGame` using `volatile` for thread safety

## Testing Framework (Custom)

### Functional Testing Utilities
Use project-specific test utilities in `core/src/test/java/com/military/coordination/util/`:

```java
// Test pure functions
TestFunctionalUtils.assertPureFunction(input, function);
TestFunctionalUtils.assertImmutable(original, operation);

// Test performance
TestGameLoopUtils.assertPerformanceUnderLoad(operation, iterations, maxTime);

// Test data transformations
TestFunctionalUtils.assertTransformation(input, transform, expected);
```

### Test Structure Convention
- Use `@DisplayName` for descriptive test names
- Nested test classes for logical grouping
- AssertJ for fluent assertions
- 80%+ coverage target

## Essential Workflows

### Development Commands (VS Code)
- **F5**: Run with debugging (auto-detects console vs desktop)
- **Ctrl+Shift+P → Tasks**: Access Maven tasks
- Available tasks: `Maven: Compile`, `Run Desktop Game`, `Run Console Game`

### Maven Commands
```bash
mvn clean compile                    # Build all modules
mvn test                            # Run tests with coverage
mvn exec:java -pl desktop           # Run desktop version
mvn exec:java -pl core              # Run console version
```

### Current Development Priority (Epic 2)
**Core Data Models** - Focus on immutable data structures:
1. Position/Coordinate System (#8)
2. Unit Data Structure (#9)
3. Resource Management (#10)
4. Game State Management (#11)

## Project-Specific Conventions

### Package Structure
- `model/`: Immutable data records (Command, CommandType, Priority, etc.)
- `system/`: Pure function collections (CommandSystem, etc.)
- `component/`: ECS-style components (GridCoordinate, GridActorMap)
- `utils/`: Functional utilities (GridUtilities with pure functions)
- `manager/`: Immutable containers (GridManager, CoordinateManager)

### Code Style
- **Records over classes** for data
- **Final classes** for utilities
- **Pure functions** marked in javadoc
- **Functional composition** over inheritance
- **Stream operations** for collections

### Game-Specific Validations
When implementing commands, respect these constraints:
- Command costs must consider ACE system variables
- All signal tower communication has range/integrity limits
- Unit trust affects command interpretation and success rates
- 5-second tick system drives all real-time processing
- Priority queue processing (HIGH=1, NORMAL=2, LOW=3)

## Integration Points

### LibGDX Graphics
- Desktop module uses LibGDX for rendering
- Core module provides console fallback
- Texture loading in `MilitaryCoordinationGame.initializeGraphics()`
- Resources: `core/src/main/resources/images/`

### External Dependencies
- **LibGDX 1.13.5**: Graphics and game framework
- **JUnit 5**: Testing with custom utilities
- **AssertJ**: Fluent assertions
- **Java 21**: Records, pattern matching, virtual threads

Focus on **strategic command simulation** mechanics, **functional programming patterns**, and **real-time 5-second tick processing** when implementing features.
