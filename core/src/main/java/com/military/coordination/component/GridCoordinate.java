package com.military.coordination.component;

public record GridCoordinate(int id, int row, int col) {
    /**
     * Creates a new GridCoordinate with the specified coordinates.
     *
     * @param row The row-coordinate.
     * @param col The column-coordinate.
     */
    public GridCoordinate {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
    }

    /**
     * Creates a grid coordinate at origin (0, 0).
     */
    public GridCoordinate(final int originId) {
        this(originId, 0, 0); // Default to origin
    }

    /**
     * Military-style grid reference (e.g., "A-5", "B-12").
     */
    public String toGridReference() {
        char letter = (char) ('A' + row);
        return letter + "-" + (col + 1);
    }

    /**
     * Returns a string representation of the grid coordinate.
     *
     * @return A string in the format "Grid(x, y)".
     */
    @Override
    public String toString() {
        return String.format("Grid(%d, %d)", row, col);
    }

}
