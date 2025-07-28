package com.military.coordination.model;

/**
 * Enumeration of possible signal statuses for a unit.
 * <ul>
 * <li>{@link #GREEN} - Full signal integrity, no delay.</li>
 * <li>{@link #YELLOW} - Degraded signal, moderate delay.</li>
 * <li>{@link #RED} - Critical signal loss, high delay or blackout.</li>
 * </ul>
 */
public enum SignalStatus {
    /**
     * Full signal integrity, no delay.
     * See {@link SignalStatus} for details.
     */
    GREEN,
    /**
     * Degraded signal, moderate delay.
     * See {@link SignalStatus} for details.
     */
    YELLOW,
    /**
     * Critical signal loss, high delay or blackout.
     * See {@link SignalStatus} for details.
     */
    RED
}
