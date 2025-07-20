package com.military.coordination.model;
/**
 * Immutable value object representing a position in 2D space.
 * Used for unit positioning, target locations, and coordinate calculations.
 *
 * Pure data structure - no behavior/logic methods.
 * Use PositionOperations utility class for calculations and operations.
 */
public record Position(int x, int y) {
}
