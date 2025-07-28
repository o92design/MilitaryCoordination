package com.military.coordination.component;

/**
 * Global constants for component value limits in the Military Coordination
 * System.
 * Centralizes all max values for trust, stress, and signals.
 */
public final class GlobalsComponent {
    /** Maximum allowed trust value. */
    public static final int MAX_TRUST = 100;
    /** Maximum allowed stress value. */
    public static final int MAX_STRESS = 100;
    /** Maximum allowed signal value. */
    public static final int MAX_SIGNALS = 100;

    private GlobalsComponent() {
        // Prevent instantiation
    }
}
