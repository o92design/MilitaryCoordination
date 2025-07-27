package com.military.coordination.actor;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.military.coordination.model.TacticalGrid;

public class GridActor extends Actor {
    public UUID id;
    public TacticalGrid grid;
    /**
     * Creates a new GridActor with the specified grid.
     * @param id The unique identifier for the grid actor.
     * @param grid The tactical grid associated with this actor.
     */
    public GridActor(final UUID id, final TacticalGrid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        this.id = id;
        this.grid = grid;
    }
}
