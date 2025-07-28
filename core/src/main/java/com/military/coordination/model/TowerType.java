package com.military.coordination.model;

/**
 * Enumeration of tower types for communication preference.
 * <ul>
 * <li>{@link #STATIC} - Permanent, high-integrity infrastructure tower.</li>
 * <li>{@link #FIELD} - Deployable field tower for temporary operations.</li>
 * <li>{@link #CIVILIAN} - Civilian relay, low security, high risk of
 * interception.</li>
 * <li>{@link #DRONE} - Mobile drone node, flexible but limited range.</li>
 * </ul>
 */
public enum TowerType {
    /**
     * Permanent, high-integrity infrastructure tower.
     * See {@link TowerType} for details.
     */
    STATIC,
    /**
     * Deployable field tower for temporary operations.
     * See {@link TowerType} for details.
     */
    FIELD,
    /**
     * Civilian relay, low security, high risk of interception.
     * See {@link TowerType} for details.
     */
    CIVILIAN,
    /**
     * Mobile drone node, flexible but limited range.
     * See {@link TowerType} for details.
     */
    DRONE
}
