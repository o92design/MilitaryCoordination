# GitHub Issue Templates

This project uses structured issue templates to maintain consistency and ensure all necessary information is captured when creating issues.

## Available Templates

### 🎯 Epic Template (`epic.yml`)
**Purpose**: Create major feature Epics that represent significant capabilities

**Use when**:
- Planning major features or system components
- Breaking down large development efforts
- Setting up milestones and project phases

**Key fields**:
- Epic goals and objectives
- Task breakdown (to be created as separate issues)
- Milestone assignment
- Acceptance criteria
- Dependencies and timeline

### ⚡ Feature/Task Template (`feature.yml`)
**Purpose**: Create specific development tasks and features

**Use when**:
- Implementing specific functionality
- Working on data structures or algorithms
- Adding new capabilities to existing components
- Refactoring or improving code

**Key fields**:
- Component assignment (core, model, engine, ai, util)
- Task type (feature, refactor, performance, testing)
- Technical approach (functional programming focus)
- Acceptance criteria with testing requirements

### 🐛 Bug Report Template (`bug_report.yml`)
**Purpose**: Report bugs and issues in the system

**Use when**:
- Something isn't working as expected
- Errors or exceptions occur
- Functionality is broken or incomplete

**Key fields**:
- Reproduction steps
- Expected vs actual behavior
- Environment information
- Error logs and stack traces
- Severity level

### 🚀 Performance Issue Template (`performance.yml`)
**Purpose**: Report performance problems or optimization needs

**Use when**:
- Frame rate drops below target (60 FPS)
- Memory usage is too high or grows over time
- CPU usage is excessive
- Response times are slow
- Algorithms need optimization

**Key fields**:
- Performance metrics (current vs target)
- Profiling data and measurements
- Reproduction scenario
- Impact assessment
- Optimization suggestions

### 📚 Documentation Template (`documentation.yml`)
**Purpose**: Request documentation improvements or additions

**Use when**:
- API documentation is missing
- User guides need updates
- Code needs better comments
- Architecture documentation is needed
- Tutorials or examples are required

**Key fields**:
- Documentation type (API, user guide, tutorial, etc.)
- Target audience
- Content outline and structure
- Examples and code samples needed

## Issue Template Guidelines

### General Principles
1. **Be Specific**: Provide clear, actionable descriptions
2. **Include Context**: Link to related issues, Epics, or documentation
3. **Set Priorities**: Use priority levels to guide development focus
4. **Define Acceptance**: Clear criteria for when work is complete
5. **Consider Testing**: Include testing requirements and approaches

### Functional Programming Focus
When creating issues, especially for features, consider:
- **Immutability**: Are data structures immutable?
- **Pure Functions**: Are functions side-effect free?
- **Composition**: How do functions compose together?
- **Stream Processing**: Can operations use streams efficiently?
- **Type Safety**: Are types used effectively?

### Real-Time System Considerations
For performance and game loop related issues:
- **Frame Rate**: Target 60 FPS minimum
- **Memory Management**: Avoid allocations in hot paths
- **Latency**: Keep response times under 16ms for critical operations
- **Scalability**: Consider performance with 1000+ units
- **Profiling**: Include actual measurements and profiling data

### Component Organization
Issues are organized by component:
- **core**: Central game logic and coordination
- **model**: Data structures (Unit, Position, Command, WorldState)
- **engine**: Game loop, state management, timing
- **ai**: Unit behavior, pathfinding, decision making
- **util**: Utilities, helper functions, algorithms
- **test**: Testing framework and test utilities

### Labels and Projects
All issues are automatically:
- Labeled appropriately (epic, bug, enhancement, etc.)
- Added to the main project board
- Assigned to the project owner
- Linked to milestones where appropriate

## Workflow Integration

### Epic → Task Breakdown
1. Create Epic using Epic template
2. Break Epic into specific tasks
3. Create individual Feature/Task issues for each task
4. Link tasks to Epic in description
5. Track progress on project board

### Issue Lifecycle
1. **Backlog**: New issues start here
2. **Ready**: Issues ready for development (requirements clear)
3. **In Progress**: Actively being worked on
4. **Code Review**: Implementation complete, under review
5. **Testing**: Being tested (automated and manual)
6. **Done**: Completed and merged

### Priority Levels
- **High**: Critical for current milestone, blocks other work
- **Medium**: Important but not blocking, should be completed soon
- **Low**: Nice to have, can be deferred if needed

## Best Practices

### Before Creating Issues
1. Search existing issues to avoid duplicates
2. Check if it belongs to an existing Epic
3. Consider if it should be broken down further
4. Ensure you have enough information to complete the work

### When Writing Issues
1. Use clear, descriptive titles
2. Provide sufficient context and background
3. Include links to related issues or documentation
4. Specify testing requirements
5. Consider performance implications

### During Development
1. Update issue progress regularly
2. Link commits to issues using "fixes #issue-number"
3. Update documentation as needed
4. Add tests before considering complete
5. Request code review when ready

These templates support the project's focus on data-oriented functional programming, real-time performance requirements, and structured project management through GitHub Projects.
