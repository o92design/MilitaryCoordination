package com.military.coordination.model;

/**
 * Represents the current operational status of a military unit.
 * This affects what actions the unit can perform and how it responds to commands.
 */
public enum UnitStatus {
    /**
     * Unit is ready for action and can accept new commands
     */
    READY("Ready for action"),

    /**
     * Unit is currently moving to a new position
     */
    MOVING("Moving to position"),

    /**
     * Unit is engaged in combat operations
     */
    ENGAGED("Engaged in combat"),

    /**
     * Unit is performing reconnaissance or surveillance
     */
    RECONNOITERING("Conducting reconnaissance"),

    /**
     * Unit is resupplying or being maintained
     */
    RESUPPLYING("Resupplying"),

    /**
     * Unit has taken significant damage and has limited capabilities
     */
    DAMAGED("Damaged - limited capability"),

    /**
     * Unit is out of action and cannot perform tasks
     */
    DISABLED("Disabled - no capability");

    private final String description;

    UnitStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Determines if the unit can accept new action commands in this status.
     *
     * @return true if the unit can perform actions, false otherwise
     */
    public boolean canAcceptCommands() {
        return this == READY;
    }

    /**
     * Determines if the unit has any operational capability in this status.
     *
     * @return true if the unit has some capability, false if completely disabled
     */
    public boolean hasCapability() {
        return this != DISABLED;
    }
}
