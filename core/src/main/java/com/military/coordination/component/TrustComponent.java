package com.military.coordination.component;

/**
 * Component representing the trust state of an entity (soldier, leader, etc.).
 * Immutable and used in ECS-style systems.
 *
 * @param trust the current trust value (0-100, inclusive)
 */
public record TrustComponent(int trust) {
    /**
     * Compact constructor with validation.
     *
     * @param trust the current trust value (0-100, inclusive)
     * @throws IllegalArgumentException if trust is out of range
     */
    public TrustComponent {
        if (trust < 0 || trust > GlobalsComponent.MAX_TRUST) {
            throw new IllegalArgumentException("Trust must be between 0 and " + GlobalsComponent.MAX_TRUST);
        }
    }
}
