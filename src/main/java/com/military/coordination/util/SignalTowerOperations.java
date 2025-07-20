package com.military.coordination.util;

import java.util.List;

import com.military.coordination.model.Position;
import com.military.coordination.model.SignalTower;
import com.military.coordination.model.Unit;

/**
 * Pure functional operations for SignalTower objects.
 * Following functional programming principles by separating data from behavior.
 * All methods are static, pure functions with no side effects.
 */
public final class SignalTowerOperations {

    // Private constructor to prevent instantiation
    private SignalTowerOperations() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Checks if a position is within the transmission range of a signal tower.
     * Pure function using circular range calculation.
     *
     * @param tower the signal tower
     * @param targetPosition the position to check
     * @return true if the position is within transmission range
     * @throws IllegalArgumentException if tower or position is null
     */
    public static boolean isWithinTransmissionRange(SignalTower tower, Position targetPosition) {
        if (tower == null) {
            throw new IllegalArgumentException("Signal tower cannot be null");
        }
        if (targetPosition == null) {
            throw new IllegalArgumentException("Target position cannot be null");
        }

        return PositionOperations.isWithinRange(tower.position(), targetPosition, tower.transmissionRange());
    }

    /**
     * Checks if a unit is within the transmission range of a signal tower.
     * Pure function using the unit's current position.
     *
     * @param tower the signal tower
     * @param unit the unit to check
     * @return true if the unit is within transmission range
     * @throws IllegalArgumentException if tower or unit is null
     */
    public static boolean isUnitInRange(SignalTower tower, Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        return isWithinTransmissionRange(tower, unit.position());
    }

    /**
     * Filters a list of units to only include those within transmission range.
     * Pure function returning a new filtered list.
     *
     * @param tower the signal tower
     * @param units the list of units to filter
     * @return a new list containing only units within range
     * @throws IllegalArgumentException if tower or units list is null
     */
    public static List<Unit> getUnitsInRange(SignalTower tower, List<Unit> units) {
        if (tower == null) {
            throw new IllegalArgumentException("Signal tower cannot be null");
        }
        if (units == null) {
            throw new IllegalArgumentException("Units list cannot be null");
        }

        return units.stream()
                   .filter(unit -> isUnitInRange(tower, unit))
                   .toList();
    }

    /**
     * Checks if two signal towers have overlapping transmission ranges.
     * Pure function using distance between towers and their ranges.
     *
     * @param tower1 the first signal tower
     * @param tower2 the second signal tower
     * @return true if their transmission ranges overlap
     * @throws IllegalArgumentException if either tower is null
     */
    public static boolean hasOverlappingRange(SignalTower tower1, SignalTower tower2) {
        if (tower1 == null || tower2 == null) {
            throw new IllegalArgumentException("Signal towers cannot be null");
        }

        double distanceBetweenTowers = PositionOperations.calculateDistance(
            tower1.position(), tower2.position()
        );
        double combinedRange = tower1.transmissionRange() + tower2.transmissionRange();

        return distanceBetweenTowers <= combinedRange;
    }

    /**
     * Calculates the effective signal strength at a given position.
     * Pure function considering distance from tower and signal degradation.
     *
     * @param tower the signal tower
     * @param targetPosition the position to calculate signal strength for
     * @return the effective signal strength (0.0 to 1.0 multiplier)
     * @throws IllegalArgumentException if tower or position is null
     */
    public static double calculateEffectiveSignalStrength(SignalTower tower, Position targetPosition) {
        if (tower == null) {
            throw new IllegalArgumentException("Signal tower cannot be null");
        }
        if (targetPosition == null) {
            throw new IllegalArgumentException("Target position cannot be null");
        }

        double distance = PositionOperations.calculateDistance(tower.position(), targetPosition);

        // If outside transmission range, no signal
        if (distance > tower.transmissionRange()) {
            return 0.0;
        }

        // Linear signal degradation: strongest at tower (1.0), weakest at edge (0.1)
        double signalRatio = 1.0 - (distance / tower.transmissionRange() * 0.9);
        return Math.max(0.1, signalRatio);
    }

    /**
     * Finds the optimal position for a new signal tower to cover given positions.
     * Pure function calculating centroid of target positions.
     *
     * @param targetPositions the positions that need coverage
     * @return the optimal position for signal tower placement
     * @throws IllegalArgumentException if positions list is null or empty
     */
    public static Position findOptimalTowerPosition(List<Position> targetPositions) {
        if (targetPositions == null || targetPositions.isEmpty()) {
            throw new IllegalArgumentException("Target positions cannot be null or empty");
        }

        int totalX = targetPositions.stream().mapToInt(Position::x).sum();
        int totalY = targetPositions.stream().mapToInt(Position::y).sum();
        int count = targetPositions.size();

        return new Position(totalX / count, totalY / count);
    }
}
