package com.military.coordination.component;

import java.util.Map;
import java.util.Set;

public record GridActorMap(Map<GridCoordinate, Set<Integer>> actorMap) {
    /**
     * Creates a new GridActorMap with the specified map.
     *
     * @param actorMap The map of grid coordinates to sets of actor IDs.
     */
    public GridActorMap {
        if (actorMap == null) {
            throw new IllegalArgumentException("Actor map cannot be null");
        }

        actorMap.forEach((coordinate, actors) -> {
            if (coordinate == null || actors == null) {
                throw new IllegalArgumentException("Grid coordinate and actors cannot be null");
            }
            if (actors.isEmpty()) {
                throw new IllegalArgumentException("Actor set cannot be empty for coordinate: " + coordinate);
            }
        });

        // Ensure all coordinates are valid
        actorMap.keySet().forEach(coordinate -> {
            if (coordinate.row() < 0 || coordinate.col() < 0) {
                throw new IllegalArgumentException("Coordinates must be non-negative: " + coordinate);
            }
        });

        // Ensure all actor IDs are positive
        actorMap.values().forEach(actors -> {
            actors.forEach(actorId -> {
                if (actorId < 0) {
                    throw new IllegalArgumentException("Actor ID must be positive: " + actorId);
                }
            });
        });
    }

    /**
     * Returns a string representation of the grid actor map.
     *
     * @return A string in the format "GridActorMap(actorMap)".
     */
    @Override
    public String toString() {
        return String.format("GridActorMap(%s)", actorMap);
    }
}
