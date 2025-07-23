package com.military.coordination.system;

import java.time.Instant;

import com.military.coordination.model.Command;
import com.military.coordination.model.CommandType;


/**
 * Functional command system providing pure functions for command operations.
 * Separates business logic from data structures following functional programming principles.
 */
public final class CommandSystem {
    public static final double MULTIPLIER_BASE = 100;
    public static final int MINIMUM_BASE_COST = 1;

    private CommandSystem() {
        // Utility class - no instances
    }

    /**
     * Check if a command has exceeded its timeout duration.
     * Pure function - no side effects, deterministic for given inputs.
     *
     * @param command The command to check
     * @param currentTime The current time for timeout comparison
     * @return true if the command has timed out
     */
    public static boolean hasTimedOut(Command command, Instant currentTime) {
        return currentTime.isAfter(command.createdAt().plus(command.timeout()));
    }

    /**
     * Convenience method using current system time.
     */
    public static boolean hasTimedOut(Command command) {
        return hasTimedOut(command, Instant.now());
    }

    /**
     * Calculate command execution cost based on game mechanics.
     * Pure function implementing the Tower Trust System cost algorithm.
     *
     * @param command The command to calculate cost for
     * @param unitTrust Trust level of the unit (0-100)
     * @param unitStress Stress level of the unit (0-100)
     * @param signalStrength Signal strength for transmission (0-100)
     * @return Calculated cost for command execution
     */
    public static int calculateCost(Command command, int unitTrust, int unitStress, int signalStrength) {
        // Validate inputs
        validatePercentage(unitTrust, "Unit trust");
        validatePercentage(unitStress, "Unit stress");
        validatePercentage(signalStrength, "Signal strength");

        // Base cost for command type
        int baseCost = getBaseCost(command.type());

        // Adjust based on unit state (higher trust/lower stress = lower cost)
        double trustMultiplier = (MULTIPLIER_BASE - unitTrust) / MULTIPLIER_BASE;
        double stressMultiplier = unitStress / MULTIPLIER_BASE;
        double signalMultiplier = (MULTIPLIER_BASE - signalStrength) / MULTIPLIER_BASE;

        return Math.max(MINIMUM_BASE_COST, (int) (baseCost * (MINIMUM_BASE_COST + trustMultiplier + stressMultiplier + signalMultiplier)));
    }

    /**
     * Get the base cost for a command type.
     * Pure function mapping command types to their base costs.
     */
    public static int getBaseCost(CommandType type) {
        return switch (type) {
            case EMERGENCY -> 3;
            case RECONNAISSANCE -> 2;
            case MOVE -> 2;
            case ESTABLISH_COMMS -> 4;
            case STATUS_REPORT -> 1;
        };
    }

    /**
     * Calculate the priority score for command queue ordering.
     * Lower scores = higher priority (processed first).
     */
    public static int getPriorityScore(Command command) {
        return command.priority().getValue();
    }

    /**
     * Check if a command is urgent (high priority or emergency type).
     */
    public static boolean isUrgent(Command command) {
        return command.priority() == com.military.coordination.model.Priority.HIGH
            || command.type() == CommandType.EMERGENCY;
    }

    /**
     * Validate percentage values are in valid range.
     */
    private static void validatePercentage(int value, String fieldName) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(fieldName + " must be between 0 and 100, got: " + value);
        }
    }
}
