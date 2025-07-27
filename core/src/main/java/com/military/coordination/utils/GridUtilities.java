package com.military.coordination.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.military.coordination.actor.GridActor;
import com.military.coordination.component.GridActorMap;
import com.military.coordination.component.GridCoordinate;
import com.military.coordination.manager.CoordinateManager;
import com.military.coordination.manager.GridManager;
import com.military.coordination.model.TacticalGrid;

/**
 * Pure functional system for operating on TacticalGrid data.
 * All methods are pure functions that return new state rather than mutating existing state.
 */
public final class GridUtilities {
    private GridUtilities() {
        // Utility class - no instantiation
    }

    /**
     * Creates a new empty tactical grid.
     * @param gridManager The grid manager to register the new grid with.
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     * @param cellSize The size of each grid cell in pixels.
     * @return A grid ID.
     */
    public static UUID createGrid(final GridManager gridManager,
                                final int rows,
                                final int cols,
                                final int cellSize) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }
        if (cellSize <= 0) {
            throw new IllegalArgumentException("Grid size must be positive");
        }

        // Generate a unique ID for the grid
        UUID gridId = UUID.randomUUID();
        TacticalGrid grid = new TacticalGrid(rows, cols, cellSize);
        gridManager.grids().put(gridId, new GridActor(gridId, grid));

        return gridId;
    }

    /**
     * Creates a new grid and initializes its coordinates.
     * @param gridManager The grid manager to register the new grid with.
     * @param coordinateManager The coordinate manager to initialize coordinates.
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     * @param cellSize The size of each grid cell in pixels.
     * @return A grid ID.
     */
    public static UUID createGridAndInitializeCoordinates(
        final GridManager gridManager,
        final CoordinateManager coordinateManager,
        final int rows,
        final int cols,
        final int cellSize
    ) {
        UUID gridId = createGrid(gridManager, rows, cols, cellSize);
        initializeCoordinates(coordinateManager, gridId, rows, cols);
        return gridId;
    }


    /**
     * Initialize grid coordinates for a given grid.
     * @param coordinateManager The coordinate manager to register the coordinates with.
     * @param gridId The ID of the grid to initialize coordinates for.
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     */
    public static void initializeCoordinates(final CoordinateManager coordinateManager,
                                            final UUID gridId,
                                            final int rows,
                                            final int cols
                                            ) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }

        // Pre-compute the coordinates for the grid
        GridCoordinate[] coordinates = new GridCoordinate[rows * cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int coordinateId = row * cols + col;
                coordinates[coordinateId] = new GridCoordinate(coordinateId, row, col);
            }
        }
        coordinateManager.coordinates.put(gridId, coordinates);
    }

    /**
     * Get a grid actor by its ID.
     * @param gridManager The grid manager to search in.
     * @param gridId The ID of the grid to find.
     * @return An Optional containing the GridActor if found, or empty if not found.
     */
    public static GridActor getGridById(final GridManager gridManager, final UUID gridId) {
        return gridManager.grids().get(gridId);
    }

    /**
     * Convert grid coordinate to world position (center of cell).
     * @param coordinate The grid coordinate to convert.
     * @param gridSize The size of each grid cell in pixels.
     * @param worldOffset The world offset for the grid.
     * @return Vector2 representing the world position of the grid coordinate.
     */
    public static Vector2 toWorldPosition(final GridCoordinate coordinate, final int gridSize,
                                          final Vector2 worldOffset) {
        return new Vector2(coordinate.col() * gridSize + gridSize / 2f + worldOffset.x,
                           coordinate.row() * gridSize + gridSize / 2f + worldOffset.y);
    }

    /**
     * Convert world position to grid coordinate.
     * @param gridId The ID of the grid to convert to.
     * @param coordinateManager The coordinate manager containing valid coordinates.
     * @param worldPosition The world position to convert.
     * @param cellSize The size of each grid cell in pixels.
     * @param cols The number of columns in the grid.
     * @return GridCoordinate corresponding to the world position.
     */
    public static GridCoordinate toGridCoordinate(
                                                final UUID gridId,
                                                final CoordinateManager coordinateManager,
                                                final Vector2 worldPosition,
                                                final int cellSize,
                                                final int cols
                                                ) {
        int row = (int) (worldPosition.y / cellSize);
        int col = (int) (worldPosition.x / cellSize);
        int index = row * cols + col;

        if (index < 0 || index >= coordinateManager.coordinates.get(gridId).length) {
            Gdx.app.log("Warning", "Invalid grid coordinate position: " + worldPosition
                           + " for grid ID: " + gridId + " [index: " + index + "]");
            return null;  // Return null if the position is invalid
        }

        return coordinateManager.coordinates.get(gridId)[index];
    }

    /**
     * Convert grid coordinate to world position (center of cell).
     * @param grid The tactical grid to use for conversion.
     * @param coordinate The grid coordinate to convert.
     * @return Vector2 representing the world position of the grid coordinate.
     */
    public static Vector2 gridToWorld(final GridActor grid, final GridCoordinate coordinate) {
        return new Vector2(
            coordinate.col() * grid.grid.cellSize() + grid.grid.cellSize() / 2f + grid.getX(),
            coordinate.row() * grid.grid.cellSize() + grid.grid.cellSize() / 2f + grid.getY()
        );
    }

    /**
     * Check if grid coordinate is valid/within bounds.
     * @param grid The tactical grid to check against.
     * @param coordinate The grid coordinate to validate.
     * @return true if the coordinate is valid, false otherwise.
     *        Validates that the coordinate is within the grid's dimensions.
     */
    public static boolean isValidGrid(final TacticalGrid grid, final GridCoordinate coordinate) {
        return coordinate.row() >= 0 && coordinate.row() < grid.rows()
               && coordinate.col() >= 0 && coordinate.col() < grid.cols();
    }

    /**
     * Map an actor to a grid position.
     * @param actorMap The current grid actor map.
     * @param coordinate The grid coordinate to add the actor to.
     * @param actorId The actorId to add at the specified coordinate.
     * @return A new TacticalGrid with the actor added - does not mutate the original.
     */
    public static GridActorMap mapActorId(final GridActorMap actorMap,
                                          final GridCoordinate coordinate,
                                          final Integer actorId) {
        if (coordinate == null) {
            throw new IllegalArgumentException("Grid coordinate cannot be null");
        }

        // Create a deep copy of the grid actor map with the new actor added
        Map<GridCoordinate, Set<Integer>> copyMap = new HashMap<>(actorMap.actorMap());
        copyMap.put(coordinate, Set.of(actorId));

        return new GridActorMap(copyMap);
    }

    /**
     * Remove an actors from a grid position.
     * @param actorMap The current grid actor map.
     * @param coordinate The grid coordinate to remove the actor from.
     * @return A new GridActorMap with the specified coordinate removed.
     * @throws IllegalArgumentException if no actor exists at the specified coordinate.
     */
    public static GridActorMap removeActors(final GridActorMap actorMap, final GridCoordinate coordinate) {
        if (actorMap == null || actorMap.actorMap() == null) {
            throw new IllegalArgumentException("Actor map cannot be null");
        }
        if (!actorMap.actorMap().containsKey(coordinate)) {
            throw new IllegalArgumentException("No actors at position: " + coordinate);
        }

        // Create a new map with the coordinate removed
        Map<GridCoordinate, Set<Integer>> newMap = new HashMap<>(actorMap.actorMap());
        newMap.remove(coordinate);
        return new GridActorMap(newMap);
    }

    /**
     * Remove an actor from a grid position.
     * @param actorMap The current grid actor map.
     * @param coordinate The grid coordinate to remove the actor from.
     * @param actorId The ID of the actor to remove.
     * @return A new GridActorMap with the actor removed from the specified coordinate.
     * @throws IllegalArgumentException if the actor does not exist at the specified coordinate.
     */
    public static GridActorMap removeActor(final GridActorMap actorMap,
                                            final GridCoordinate coordinate,
                                            final Integer actorId) {
        if (actorMap == null || actorMap.actorMap() == null) {
            throw new IllegalArgumentException("Actor map cannot be null");
        }
        if (!actorMap.actorMap().containsKey(coordinate)) {
            throw new IllegalArgumentException("No actor at position: " + coordinate + " [actorId: " + actorId + "]");
        }

        Set<Integer> actorsAtCoordinate = actorMap.actorMap().get(coordinate);
        if (actorsAtCoordinate == null || !actorsAtCoordinate.contains(actorId)) {
            throw new IllegalArgumentException("Actor ID " + actorId + " not found at coordinate " + coordinate);
        }

        // Create a new map with the actor removed (functional approach)
        Map<GridCoordinate, Set<Integer>> newMap = new HashMap<>(actorMap.actorMap());
        Set<Integer> remainingActors = new HashSet<>(actorsAtCoordinate);
        remainingActors.remove(actorId);

        if (remainingActors.isEmpty()) {
            newMap.remove(coordinate);
        } else {
            newMap.put(coordinate, Set.copyOf(remainingActors));
        }

        return new GridActorMap(newMap);
    }


    /**
     * Move an actor from one grid position to another.
     * @param actorMap The current grid actor map.
     * @param actorId The ID of the actor to move.
     * @param from The grid coordinate to move the actor from.
     * @param to The grid coordinate to move the actor to.
     * @return A new GridActorMap with the actor moved.
     * @throws IllegalArgumentException if the actor does not exist at the 'from' position.
     */
    public static GridActorMap moveActor(final GridActorMap actorMap,
                                        final int actorId,
                                        final GridCoordinate from,
                                        final GridCoordinate to) {
        if (actorMap == null || actorMap.actorMap() == null) {
            throw new IllegalArgumentException("Actor map cannot be null");
        }
        if (!actorMap.actorMap().containsKey(from)) {
            throw new IllegalArgumentException("No actor at position: " + from + " [actorId: " + actorId + "]");
        }

        Set<Integer> actorsAtFrom = actorMap.actorMap().get(from);
        if (!actorsAtFrom.contains(actorId)) {
            throw new IllegalArgumentException("Actor ID " + actorId + " not found at coordinate " + from);
        }

        // Create a new map with the actor moved (functional approach)
        Map<GridCoordinate, Set<Integer>> newMap = new HashMap<>(actorMap.actorMap());

        // Remove actor from old position
        Set<Integer> remainingActors = new HashSet<>(actorsAtFrom);
        remainingActors.remove(actorId);

        if (remainingActors.isEmpty()) {
            newMap.remove(from); // Remove empty coordinate
        } else {
            newMap.put(from, Set.copyOf(remainingActors)); // Immutable copy
        }

        // Add actor to new position
        Set<Integer> actorsAtTo = newMap.getOrDefault(to, new HashSet<>());
        Set<Integer> newActorsAtTo = new HashSet<>(actorsAtTo);
        newActorsAtTo.add(actorId);
        newMap.put(to, Set.copyOf(newActorsAtTo)); // Immutable copy

        return new GridActorMap(newMap);
    }

    /**
     * Check if a grid cell is occupied.
     * @param actorMap The grid actor map to check.
     * @param coordinate The grid coordinate to check.
     * @return true if occupied, false otherwise.
     * Pure function - no side effects.
     */
    public static boolean isOccupied(final GridActorMap actorMap, final GridCoordinate coordinate) {
        if (actorMap == null || actorMap.actorMap() == null) {
            throw new IllegalArgumentException("Actor map cannot be null");
        }
        return actorMap.actorMap().containsKey(coordinate)
               && !actorMap.actorMap().get(coordinate).isEmpty();
    }

    /**
     * Get all occupied grid coordinates.
     * @param actorMap The grid actor map to query.
     * @return A Set of all occupied grid coordinates.
     */
    public static Set<GridCoordinate> getOccupiedCoordinates(final GridActorMap actorMap) {
        if (actorMap == null || actorMap.actorMap() == null) {
            throw new IllegalArgumentException("Actor map cannot be null");
        }
        return actorMap.actorMap().keySet();
    }

    /**
     * Get all actor ids currently on the grid.
     * @param actorMap The grid actor map to query.
     * @return A Set of all actor ids currently on the grid.
     */
    public static Set<Integer> getAllActorIds(final GridActorMap actorMap) {
        if (actorMap == null || actorMap.actorMap() == null) {
            throw new IllegalArgumentException("Actor map cannot be null");
        }
        return actorMap.actorMap().values().stream()
               .flatMap(Set::stream)
               .collect(Collectors.toSet());
    }

    /**
     * Find the grid coordinate of a specific actor.
     * @param actorMap The grid actor map to search.
     * @param actorId The actor to find.
     * @return Optional containing the GridCoordinate if found, or empty if not found.
     * @throws IllegalArgumentException if the actor is null.
     */
    public static Optional<GridCoordinate> findActorPosition(final GridActorMap actorMap, final Integer actorId) {
        if (actorId == null) {
            throw new IllegalArgumentException("Actor must not be null");
        }
        return actorMap.actorMap().entrySet().stream()
            .filter(entry -> entry.getValue().contains(actorId))
            .map(Map.Entry::getKey)
            .findFirst();
    }

    /**
     * Generate a tactical map display string.
     * @param gridManager The grid manager containing grid information.
     * @param coordinateManager The coordinate manager for valid coordinates.
     * @param actorMap The grid actor map containing actor positions.
     * @param gridId The tactical grid to visualize.
     * @return A string representation of the tactical grid status.
     *        Each cell is represented as "X" for occupied and "." for free.
     * Pure function - creates a visualization without modifying state.
     */
    public static String generateTacticalMap(
                                            final GridManager gridManager,
                                            final CoordinateManager coordinateManager,
                                            final UUID gridId,
                                            final GridActorMap actorMap
                                            ) {
        StringBuilder map = new StringBuilder();
        map.append("TACTICAL GRID STATUS:\n");
        map.append("   ");

        TacticalGrid grid = getGridById(gridManager, gridId).grid;

        // Column headers
        for (int col = 0; col < grid.cols(); col++) {
            map.append(String.format("%2d ", col + 1));
        }
        map.append("\n");

        // Grid contents
        for (int row = 0; row < grid.rows(); row++) {
            char rowLabel = (char) ('A' + row);
            map.append(String.format("%c: ", rowLabel));
            for (int col = 0; col < grid.cols(); col++) {
                GridCoordinate coord = getGridCoordinate(coordinateManager,
                                                        getGridById(gridManager, gridId),
                                                        row, col);

                if (isOccupied(actorMap, coord)) {
                    map.append(" X ");
                } else {
                    map.append(" . ");
                }
            }
            map.append("\n");
        }

        return map.toString();
    }

    /**
     * Get the grid coordinate for a specific row and column.
     * @param coordinateManager The coordinate manager to retrieve coordinates from.
     * @param grid The grid to get the coordinate for.
     * @param row The row of the grid.
     * @param col The column of the grid.
     * @return The GridCoordinate for the specified row and column.
     * @throws IllegalArgumentException if the row or column is out of bounds.
     */
    public static GridCoordinate getGridCoordinate(final CoordinateManager coordinateManager,
                                                   final GridActor grid,
                                                   final int row,
                                                   final int col
                                                   ) {
        if (row < 0 || row >= grid.grid.rows() || col < 0 || col >= grid.grid.cols()) {
            throw new IllegalArgumentException("Row and column must be within grid bounds");
        }
        int id = row * grid.grid.cols() + col;
        return coordinateManager.coordinates.get(grid.id)[id];
    }

    /**
     * Get adjacent grid coordinates (4-directional).
     * @param gridManager The grid manager containing the tactical grid.
     * @param coordinateManager The coordinate manager for valid coordinates.
     * @param gridId The ID of the tactical grid to check.
     * @param centerCoord The center coordinate to find adjacent coordinates for.
     * @return A Set of adjacent GridCoordinates that are valid within the grid bounds.
     *        If a coordinate is out of bounds, it will not be included.
     */
    public static Set<GridCoordinate> getAdjacentCoordinates(final GridManager gridManager,
                                                        final CoordinateManager coordinateManager,
                                                        final UUID gridId,
                                                        final GridCoordinate centerCoord) {
        GridActor gridActor = getGridById(gridManager, gridId);
        TacticalGrid grid = gridActor.grid;
        Set<GridCoordinate> adjacent = new HashSet<>();

        // Check all 4 directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // North, South, West, East

        for (int[] dir : directions) {
            int newRow = centerCoord.row() + dir[0];
            int newCol = centerCoord.col() + dir[1];

            // Check bounds
            if (newRow >= 0 && newRow < grid.rows() && newCol >= 0 && newCol < grid.cols()) {
                // Get the pre-stored coordinate
                GridCoordinate adjCoord = getGridCoordinate(coordinateManager, gridActor, newRow, newCol);
                adjacent.add(adjCoord);
            }
        }

        return adjacent;
    }

    /**
     * Get all free (unoccupied) adjacent coordinates.
     * @param gridManager The grid manager containing the tactical grid.
     * @param coordinateManager The coordinate manager for valid coordinates.
     * @param gridId The ID of the tactical grid to check.
     * @param actorMap The grid actor map to check occupancy.
     * @param actorId The ID of the actor to check for.
     * @return A Set of free GridCoordinates that are adjacent to the specified coordinate.
     *        If a coordinate is out of bounds or occupied, it will not be included.
     * Pure function - filters adjacent coordinates by occupancy.
     */
    public static Optional<GridCoordinate> findActorGridPosition(final GridManager gridManager,
                                                           final CoordinateManager coordinateManager,
                                                           final UUID gridId,
                                                           final GridActorMap actorMap,
                                                           final int actorId) {
        // Find the actor using existing method
        Optional<GridCoordinate> position = findActorPosition(actorMap, actorId);

        // Verify it's in the correct grid's coordinate space
        if (position.isPresent()) {
            GridCoordinate coord = position.get();
            // Verify this coordinate exists in our stored coordinate system
            try {
                GridCoordinate storedCoord = getGridCoordinate(coordinateManager,
                                                                getGridById(gridManager, gridId),
                                                                coord.row(), coord.col());
                return storedCoord.equals(coord) ? position : Optional.empty();
            } catch (IllegalArgumentException e) {
                return Optional.empty(); // Coordinate not in this grid
            }
        }

        return Optional.empty();
    }

    /**
     * Check if a grid coordinate is out of bounds.
     * @param grid The tactical grid to check against.
     * @param coord The grid coordinate to validate.
     * @return true if the coordinate is out of bounds, false otherwise.
     * Pure function - no side effects.
     */
    public static boolean isOutOfBounds(final TacticalGrid grid, final GridCoordinate coord) {
        return coord.row() < 0 || coord.row() >= grid.rows()
               || coord.col() < 0 || coord.col() >= grid.cols();
    }
}
