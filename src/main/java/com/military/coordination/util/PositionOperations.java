package com.military.coordination.util;

import com.military.coordination.model.Position;

/**
 * Pure functional operations for Position objects.
 * Following functional programming principles by separating data from behavior.
 * All methods are static, pure functions with no side effects.
 */
public final class PositionOperations {

    // Private constructor to prevent instantiation
    private PositionOperations() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Calculates the distance between two positions using Euclidean distance.
     * Pure function - same inputs always produce same output.
     *
     * @param from the starting position
     * @param to the target position
     * @return the distance between the positions
     * @throws IllegalArgumentException if either position is null
     */
    public static double calculateDistance(Position from, Position to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Positions cannot be null");
        }
        int dx = to.x() - from.x();
        int dy = to.y() - from.y();
        return Math.sqrt((double) dx * dx + (double) dy * dy);
    }

    /**
     * Calculates the Manhattan distance between two positions.
     * Pure function with clear mathematical logic.
     *
     * @param from the starting position
     * @param to the target position
     * @return the Manhattan distance between the positions
     * @throws IllegalArgumentException if either position is null
     */
    public static int calculateManhattanDistance(Position from, Position to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Positions cannot be null");
        }
        return Math.abs(to.x() - from.x()) + Math.abs(to.y() - from.y());
    }

    /**
     * Creates a new position offset by the specified amounts.
     * Pure function that creates new immutable instances.
     *
     * @param position the original position
     * @param deltaX the x offset
     * @param deltaY the y offset
     * @return a new position offset by the specified amounts
     * @throws IllegalArgumentException if position is null
     */
    public static Position createOffset(Position position, int deltaX, int deltaY) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        return new Position(position.x() + deltaX, position.y() + deltaY);
    }

    /**
     * Determines if two positions are within a specified range of each other.
     * Pure function combining distance calculation with range comparison.
     *
     * @param from the starting position
     * @param to the target position
     * @param range the maximum distance
     * @return true if positions are within range, false otherwise
     * @throws IllegalArgumentException if either position is null or range is negative
     */
    public static boolean isWithinRange(Position from, Position to, double range) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Positions cannot be null");
        }
        if (range < 0) {
            throw new IllegalArgumentException("Range cannot be negative");
        }
        return calculateDistance(from, to) <= range;
    }
}
