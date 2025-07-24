package com.military.coordination.model;

/**
 * Priority levels for military commands.
 * Lower numeric values indicate higher priority for queue processing.
 */
public enum Priority {
    HIGH(1),     // Critical priority, immediate action required
    NORMAL(2),   // Standard priority, routine operations
    LOW(3);      // Low priority, can be deferred

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
