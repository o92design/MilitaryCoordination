package com.military.coordination.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.military.coordination.actor.GridActor;
import com.military.coordination.component.GridActorMap;
import com.military.coordination.component.GridCoordinate;
import com.military.coordination.manager.CoordinateManager;
import com.military.coordination.utils.GridUtilities;

public class GridDebugRenderer {
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private boolean enabled = false;
    public Color gridLineColor = new Color(0, 1, 0, 0.7f); // Default grid line color

    public GridDebugRenderer(BitmapFont font, Color gridLineColor) {
        this.shapeRenderer = new ShapeRenderer();
        this.font = font;
        this.gridLineColor = gridLineColor;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Render debug grid. Call this method BETWEEN batch.end() and batch.begin()
     * or provide separate methods for different render phases.
     */
    public void render(SpriteBatch batch, CoordinateManager coordinateManager, GridActor grid, GridActorMap actorMap) {
        if (!enabled) return;

        // End the current batch to draw shapes
        batch.end();

        // Draw grid lines and filled shapes
        renderGridLines(grid);
        renderOccupiedCells(grid, actorMap);

        // Begin batch again for text rendering
        batch.begin();
        renderGridLabels(batch, coordinateManager, grid);
        // Don't end batch here - let the caller handle it
    }

    /**
     * Alternative: Render only shapes (call between batch.end() and batch.begin())
     */
    public void renderShapes(GridActor grid, GridActorMap actorMap) {
        if (!enabled) return;

        renderGridLines(grid);
        renderOccupiedCells(grid, actorMap);
    }

    /**
     * Alternative: Render only labels (call while batch is active)
     */
    public void renderLabels(SpriteBatch batch, CoordinateManager coordinateManager, GridActor grid) {
        if (!enabled) return;

        renderGridLabels(batch, coordinateManager, grid);
    }

    private void renderGridLines(final GridActor grid) {
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(gridLineColor);

        Vector2 origin = new Vector2(grid.getX(), grid.getY());
        float gridSize = grid.grid.cellSize();

        // Vertical lines (columns)
        for (int col = 0; col <= grid.grid.cols(); col++) {
            float x = origin.x + col * gridSize;
            shapeRenderer.line(x, origin.y, x, origin.y + grid.grid.rows() * gridSize);
        }

        // Horizontal lines (rows)
        for (int row = 0; row <= grid.grid.rows(); row++) {
            float y = origin.y + row * gridSize;
            shapeRenderer.line(origin.x, y, origin.x + grid.grid.cols() * gridSize, y);
        }

        shapeRenderer.end();
    }

    private void renderGridLabels(SpriteBatch batch, CoordinateManager coordinateManager, GridActor grid) {
        // Batch should already be active when this is called
        Color originalColor = font.getColor();
        font.setColor(Color.YELLOW);

        for (int row = 0; row < grid.grid.rows(); row++) {
            for (int col = 0; col < grid.grid.cols(); col++) {
                GridCoordinate coord = GridUtilities.getGridCoordinate(coordinateManager, grid, row, col);
                Vector2 center = GridUtilities.gridToWorld(grid, coord);

                // Draw grid reference (A-1, B-2, etc.)
                String label = coord.toGridReference();
                font.draw(batch, label, center.x - 5, center.y + 10);

                // Draw row,col coordinates smaller
                String coords = String.format("(%d,%d)", row, col);
                font.draw(batch, coords, center.x - 10, center.y - 10);
            }
        }

        font.setColor(originalColor); // Reset font color
    }

    private void renderOccupiedCells(GridActor grid, GridActorMap actorMap) {
        if (GridUtilities.getOccupiedCoordinates(actorMap).isEmpty()) {
            return; // Nothing to draw
        }

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (GridCoordinate coord : GridUtilities.getOccupiedCoordinates(actorMap)) {
            if (GridUtilities.isOutOfBounds(grid.grid, coord)) {
                continue; // Skip out-of-bounds coordinates
            }

            Vector2 center = GridUtilities.gridToWorld(grid, coord);
            float halfSize = grid.grid.cellSize() / 2f;

            shapeRenderer.rect(
                center.x - halfSize,
                center.y - halfSize,
                grid.grid.cellSize(),
                grid.grid.cellSize()
            );
        }

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
