package com.military.coordination.model;

/**
 * Enum representing the various roles a soldier can have within a military
 * unit.
 * <ul>
 * <li>{@link #INFANTRY} - Standard ground combat soldier.</li>
 * <li>{@link #SUPPORT} - Provides logistical or fire support to other
 * units.</li>
 * <li>{@link #MEDIC} - Responsible for providing medical assistance on the
 * battlefield.</li>
 * <li>{@link #ENGINEER} - Handles construction, demolition, and technical
 * tasks.</li>
 * <li>{@link #SNIPER} - Specializes in long-range precision shooting.</li>
 * <li>{@link #LEADER} - Commands and coordinates the actions of other
 * soldiers.</li>
 * </ul>
 */
public enum SoldierRole {
    /**
     * Standard ground combat soldier.
     */
    INFANTRY,
    /**
     * Provides logistical or fire support to other units.
     */
    SUPPORT,
    /**
     * Responsible for providing medical assistance on the battlefield.
     */
    MEDIC,
    /**
     * Handles construction, demolition, and technical tasks.
     */
    ENGINEER,
    /**
     * Specializes in long-range precision shooting.
     */
    SNIPER,
    /**
     * Commands and coordinates the actions of other soldiers.
     */
    LEADER
}
