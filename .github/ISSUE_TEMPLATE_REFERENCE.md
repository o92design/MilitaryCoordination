# Issue Templates Quick Reference

## When to Use Each Template

| Template | Use When | Example |
|----------|----------|---------|
| 🎯 **Epic** | Major feature/capability | "Implement Core Data Models" |
| ⚡ **Feature/Task** | Specific development work | "Create immutable Unit record" |
| 🐛 **Bug Report** | Something isn't working | "Game loop crashes with 1000+ units" |
| 🚀 **Performance** | Speed/memory issues | "Frame rate drops below 30 FPS" |
| 📚 **Documentation** | Missing/poor docs | "Add API docs for Position class" |

## Component Quick Reference

| Component | Focus Area | Examples |
|-----------|------------|----------|
| **core** | Central game logic | Coordination algorithms, main game flow |
| **model** | Data structures | Unit, Position, Command, WorldState |
| **engine** | Real-time systems | Game loop, state updates, timing |
| **ai** | Unit behavior | Pathfinding, decision making, formations |
| **util** | Helper code | Algorithms, data structures, utilities |
| **test** | Testing | Test utilities, framework improvements |

## Priority Guidelines

| Priority | When to Use | Timeline |
|----------|-------------|----------|
| **High** | Blocks development, critical bugs | Current sprint |
| **Medium** | Important features, non-critical bugs | Next sprint |
| **Low** | Nice-to-have, documentation, refactoring | Backlog |

## Functional Programming Checklist

When creating feature issues, ensure:
- [ ] Data structures are immutable
- [ ] Functions are pure (no side effects)
- [ ] Operations use streams where appropriate
- [ ] Type safety is considered
- [ ] Performance implications are evaluated

## Performance Standards

For performance-related issues:
- **Frame Rate**: Maintain 60 FPS minimum
- **Latency**: Keep critical operations under 16ms
- **Memory**: No memory leaks, stable usage
- **Scalability**: Test with 1000+ units
- **CPU**: Keep under 80% during normal operation
