package com.military.coordination.component;

/**
 * Component representing the signal strength of a unit.
 * Immutable and used in ECS-style systems.
 *
 * @param strength the current signal strength (0-100, inclusive)
 */
public record SignalStrengthComponent(int strength) {

    /**
     * Compact constructor with validation.
     *
     * @param strength the current signal strength (must not be null)
     */
    public SignalStrengthComponent {
        if (strength < 0 || strength > GlobalsComponent.MAX_SIGNAL_STRENGTH) {
            throw new IllegalArgumentException(
                    "Signal strength must be between 0 and " + GlobalsComponent.MAX_SIGNAL_STRENGTH);
        }
    }
}
