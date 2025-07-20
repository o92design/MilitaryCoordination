package com.military.coordination.model;

import java.util.List;

/**
 * Immutable value object representing a Signal Tower with position and transmission range.
 * Used for communication coordination and intel gathering within circular range.
 *
 * Pure data structure following functional programming principles.
 * Use SignalTowerOperations utility class for range calculations and operations.
 */
public record SignalTower(
    String id,
    Position position,
    int signalStrength,
        double transmissionRange,
            SignalTowerType type,
    List<Unit> reconUnits,
    List<IntelReport> intelReports
) {
    public SignalTower {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (signalStrength <= 0) {
            throw new IllegalArgumentException("Signal strength must be positive");
        }
        if (transmissionRange <= 0.0) {
            throw new IllegalArgumentException("Transmission range must be positive");
        }
        if (reconUnits == null) {
            throw new IllegalArgumentException("reconUnits cannot be null");
        }
        if (intelReports == null) {
            throw new IllegalArgumentException("intelReports cannot be null");
        }
    }

    /**
     * Creates a new SignalTower with updated position.
     * Functional update method following immutability principles.
     *
     * @param newPosition the new position for the signal tower
     * @return a new SignalTower instance with the updated position
     */
    public SignalTower withPosition(Position newPosition) {
        return new SignalTower(id, newPosition, signalStrength, transmissionRange, reconUnits, intelReports);
    }

    /**
     * Creates a new SignalTower with updated transmission range.
     * Functional update method following immutability principles.
     *
     * @param newRange the new transmission range
     * @return a new SignalTower instance with the updated range
     */
    public SignalTower withTransmissionRange(double newRange) {
        return new SignalTower(id, position, signalStrength, newRange, reconUnits, intelReports);
    }

    /**
     * Creates a new SignalTower with updated signal strength.
     * Functional update method following immutability principles.
     *
     * @param newStrength the new signal strength
     * @return a new SignalTower instance with the updated strength
     */
    public SignalTower withSignalStrength(int newStrength) {
        return new SignalTower(id, position, newStrength, transmissionRange, reconUnits, intelReports);
    }

    /**
     * Creates a new SignalTower with updated reconnaissance units.
     * Functional update method following immutability principles.
     *
     * @param newReconUnits the new list of reconnaissance units
     * @return a new SignalTower instance with the updated units
     */
    public SignalTower withReconUnits(List<Unit> newReconUnits) {
        return new SignalTower(id, position, signalStrength, transmissionRange, newReconUnits, intelReports);
    }

    /**
     * Creates a new SignalTower with updated intel reports.
     * Functional update method following immutability principles.
     *
     * @param newIntelReports the new list of intel reports
     * @return a new SignalTower instance with the updated reports
     */
    public SignalTower withIntelReports(List<IntelReport> newIntelReports) {
        return new SignalTower(id, position, signalStrength, transmissionRange, reconUnits, newIntelReports);
    }
}
