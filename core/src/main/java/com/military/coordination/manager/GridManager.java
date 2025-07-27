package com.military.coordination.manager;

import java.util.Map;
import java.util.UUID;

import com.military.coordination.actor.GridActor;

/**
 * Immutable container for grid storage and lookup.
 */
public class GridManager {
    public Map<UUID, GridActor> grids;

    public GridManager(Map<UUID, GridActor> grids) {
        this.grids = grids;
    }

    public Map<UUID, GridActor> grids() {
        return grids;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GridManager{\n");
        grids.forEach((key, value) -> {
            sb.append("  ")
                .append(key)
                .append(": ")
                .append(value)
                .append("\n");
        });
        sb.append("}");
        return sb.toString();
    }
}
