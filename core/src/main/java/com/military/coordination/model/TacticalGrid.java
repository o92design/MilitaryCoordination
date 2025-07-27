package com.military.coordination.model;

public record TacticalGrid(int rows,
                           int cols,
                           int cellSize // Size of each grid cell in pixels
                           ) {

    /**
     * Creates a new TacticalGrid with the specified dimensions.
     *
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     */
    public TacticalGrid {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }
        if (cellSize <= 0) {
            throw new IllegalArgumentException("Cell size must be positive");
        }
    }

    /**
     * Returns a string representation of the grid model.
     *
     * @return A string in the format "GridModel(rows, cols)".
     */
    @Override
    public String toString() {
        return String.format("TacticalGrid(%d, %d, %d)", rows, cols, cellSize);
    }

}
