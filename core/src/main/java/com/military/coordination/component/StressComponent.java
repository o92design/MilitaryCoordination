package com.military.coordination.component;

/**
 * Component representing the stress state of a unit.
 * Immutable and used in ECS-style systems.
 *
 * @param stress the current stress value (0-100, inclusive)
 */
public record StressComponent(int stress) {

    /**
     * Compact constructor with validation.
     *
     * @param stress the current stress value (must be non-negative)
     */
    public StressComponent {
        if (stress < 0) {
            throw new IllegalArgumentException("Stress cannot be negative");
        }
    }
}
