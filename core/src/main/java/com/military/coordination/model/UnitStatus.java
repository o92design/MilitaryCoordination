package com.military.coordination.model;

/**
 * Enumeration of possible operational statuses for a unit.
 * <ul>
 * <li>{@link #ACTIVE} - Fully operational and ready for orders.</li>
 * <li>{@link #INACTIVE} - Temporarily unavailable or on standby.</li>
 * <li>{@link #DAMAGED} - Operational but with reduced effectiveness.</li>
 * <li>{@link #DESTROYED} - No longer operational.</li>
 * </ul>
 */
public enum UnitStatus {
    /**
     * Fully operational and ready for orders.
     * See {@link UnitStatus} for details.
     */
    ACTIVE,
    /**
     * Temporarily unavailable or on standby.
     * See {@link UnitStatus} for details.
     */
    INACTIVE,
    /**
     * Operational but with reduced effectiveness.
     * See {@link UnitStatus} for details.
     */
    DAMAGED,
    /**
     * No longer operational.
     * See {@link UnitStatus} for details.
     */
    DESTROYED
}
