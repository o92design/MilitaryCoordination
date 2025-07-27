package com.military.coordination.manager;

import java.util.HashMap;
import java.util.UUID;

import com.military.coordination.component.GridCoordinate;

public class CoordinateManager {

    public HashMap<UUID, GridCoordinate[]> coordinates;

    public CoordinateManager(HashMap<UUID, GridCoordinate[]> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder("CoordinateManager{\n");
        coordinates.forEach((key, value) -> {
            sb.append("  ")
              .append(key)
              .append(": ")
              .append(java.util.Arrays.toString(value))
              .append("\n");
        });
        sb.append("  size=").append(coordinates.size()).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
