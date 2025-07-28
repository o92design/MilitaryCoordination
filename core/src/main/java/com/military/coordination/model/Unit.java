package com.military.coordination.model;

import java.util.UUID;

/**
 * Immutable data record representing a field unit in the Tower Trust System.
 * All updates are performed via withX() methods for functional purity.
 *
 * @param id           Unique identifier for the unit
 * @param name         Human-readable name or callsign
 * @param signalStatus Current signal status (GREEN/YELLOW/RED)
 * @param position     Current position on the grid
 * @param status       Current operational status
 */
public record Unit(
        UUID id,
        String name,
        SignalStatus signalStatus,
        UnitStatus status) {
    /**
     * Compact constructor for validation.
     *
     * @throws IllegalArgumentException if any required field is invalid
     * @throws NullPointerException     if any required field is null
     * @throws IllegalArgumentException if name is blank or signalStatus is null
     */
    public Unit {
        if (id == null) {
            throw new IllegalArgumentException("Unit ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Unit name cannot be null or empty");
        }
        if (signalStatus == null) {
            throw new IllegalArgumentException("Signal status cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Unit status cannot be null");
        }
    }

    /**
     * Returns a new Unit with updated signal status.
     *
     * @param newSignalStatus the new signal status
     * @return a new Unit with the given signal status
     */
    public Unit withSignalStatus(final SignalStatus newSignalStatus) {
        return new Unit(id, name, newSignalStatus, status);
    }

    /**
     * Returns a new Unit with updated status.
     *
     * @param newStatus the new unit status
     * @return a new Unit with the given status
     */
    public Unit withStatus(final UnitStatus newStatus) {
        return new Unit(id, name, signalStatus, newStatus);
    }
}
