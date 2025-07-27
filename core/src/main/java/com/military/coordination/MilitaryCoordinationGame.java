package com.military.coordination;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.military.coordination.component.GridActorMap;
import com.military.coordination.component.GridCoordinate;
import com.military.coordination.debug.GridDebugRenderer;
import com.military.coordination.manager.CoordinateManager;
import com.military.coordination.manager.GridManager;
import com.military.coordination.model.Command;
import com.military.coordination.model.CommandType;
import com.military.coordination.model.Priority;
import com.military.coordination.system.CommandSystem;
import com.military.coordination.utils.GridUtilities;

/**
 * Main game class for Military Coordination tactical simulation.
 * Implements the 5-second command tick system from the game design.
 */
public class MilitaryCoordinationGame extends ApplicationAdapter {

    // Immutable game state (only modified through functional updates)
    private volatile UUID currentTacticalGridId;
    private volatile UUID towerSignalGridId;
    private volatile Command currentCommand;
    private volatile String gameStatus = "Initializing Tower Trust System...";
    private volatile int tickCount = 0;
    private volatile GridActorMap currentActorMap = new GridActorMap(Map.of());
    private volatile GridActorMap towerSignalActorMap = new GridActorMap(Map.of());

    private GridManager gridManager;
    private CoordinateManager coordinateManager;

    Actor[] actors = new Actor[3]; // Placeholder for future use

    GridDebugRenderer dGridDebugRenderer;
    GridDebugRenderer dGridDebugRendererTowerSignal;

    // Rendering components (main thread only)
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Skin skin;
    private Texture background;
    private Texture towerTexture;
    private Image tower;
    private Image tower2;
    private Image tower3;

    private volatile boolean towerMoving = false;
    private static final float MOVE_DURATION = 0.5f;

    // Game loop (separate thread)
    private ScheduledExecutorService gameLoop;

    private static final int TOWER_SIZE = 100;

    private static final int CELL_SIZE = 160;
    private static final int GRID_ROWS = 5;
    private static final int GRID_COLS = 7;

    @Override
    public void create() {
        // Initialize graphics (main thread)
        initializeGraphics();

        // Initialize game state (immutable)
        initializeGameState();

        // Start game loop (separate thread)
        startGameLoop();

        Gdx.app.log("MilitaryCoordination", "Tower Trust System initialized");
    }

    private void initializeGraphics() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.GREEN);

        stage = new Stage(new ScreenViewport());
        skin = new Skin();
        skin.add("default-font", font);

        try {
            background = new Texture(Gdx.files.internal("images/terrain/maps/lakes.png"));
        } catch (Exception e) {
            Gdx.app.log("Warning", "Background texture not found");
            background = null;
        }

        try {
            towerTexture = new Texture(Gdx.files.internal("images/towers/tower.png"));
            tower = new Image(towerTexture);
            tower2 =  new Image(towerTexture);
            tower3 = new Image(towerTexture);
        } catch (Exception e) {
            Gdx.app.log("Warning", "Tower texture not found");
            towerTexture = null;
        }

        dGridDebugRenderer = new GridDebugRenderer(font, Color.GREEN);
        dGridDebugRendererTowerSignal = new GridDebugRenderer(font, Color.MAGENTA);

        createUI();
    }

    private void initializeGameState() {
        gridManager = new GridManager(new HashMap<>());
        coordinateManager = new CoordinateManager(new HashMap<>());

        // Create initial grid state (immutable)
        currentTacticalGridId = GridUtilities.createGridAndInitializeCoordinates(
                                                            gridManager, coordinateManager,
                                                            GRID_ROWS, GRID_COLS, CELL_SIZE);

        // Create test command
        GridCoordinate targetSector = GridUtilities.getGridCoordinate(coordinateManager,
                                                                          GridUtilities.getGridById(gridManager,
                                                                                                    currentTacticalGridId),
                                                                          2, 4);
        currentCommand = new Command(
            UUID.randomUUID(),
            CommandType.RECONNAISSANCE,
            targetSector.toGridReference(),
            Priority.NORMAL,
            Duration.ofMinutes(10)
            );

            actors[0] = tower;
            int towerId = 0;
            actors[1] = tower2;
            int tower2Id = 1;
            actors[2] = tower3;
            int tower3Id = 2;

            // Create tower actor for rendering
        GridCoordinate towerPosition = GridUtilities.getGridCoordinate(coordinateManager, GridUtilities.getGridById(gridManager, currentTacticalGridId), 1, 1);
        Vector2 towerWorldPos = GridUtilities.gridToWorld(GridUtilities.getGridById(gridManager, currentTacticalGridId), towerPosition);

        //tower = new Actor();
        tower.setSize(TOWER_SIZE, TOWER_SIZE);
        tower.setPosition(towerWorldPos.x, towerWorldPos.y);
        tower.setName("TowerActor");
        stage.addActor(tower);
        currentActorMap = GridUtilities.mapActorId(
            currentActorMap,
            towerPosition,
            towerId
            );

        towerPosition = GridUtilities.getGridCoordinate(coordinateManager, GridUtilities.getGridById(gridManager, currentTacticalGridId), 2, 5);
        towerWorldPos = GridUtilities.gridToWorld(GridUtilities.getGridById(gridManager, currentTacticalGridId), towerPosition);
        tower2.setSize(TOWER_SIZE, TOWER_SIZE);
        tower2.setPosition(towerWorldPos.x, towerWorldPos.y);
        tower2.setName("TowerActor2");
        stage.addActor(tower2);
        currentActorMap = GridUtilities.mapActorId(
            currentActorMap,
            towerPosition,
            tower2Id
            );
        towerSignalGridId = GridUtilities.createGridAndInitializeCoordinates(
            gridManager, coordinateManager, 8, 4, 64
        );

        GridUtilities.getGridById(gridManager, towerSignalGridId).setX(tower2.getX());
        GridUtilities.getGridById(gridManager, towerSignalGridId).setY(tower2.getY());

        towerPosition = GridUtilities.getGridCoordinate(coordinateManager, GridUtilities.getGridById(gridManager, currentTacticalGridId), 2, 3);
        towerWorldPos = GridUtilities.gridToWorld(GridUtilities.getGridById(gridManager, currentTacticalGridId), towerPosition);
        tower3.setSize(TOWER_SIZE, TOWER_SIZE);
        tower3.setPosition(towerWorldPos.x, towerWorldPos.y);
        tower3.setName("TowerActor3");
        stage.addActor(tower3);
        currentActorMap = GridUtilities.mapActorId(
            currentActorMap,
            towerPosition,
            tower3Id
        );

        gameStatus = String.format("Command created: %s targeting %s",
            currentCommand.type(), currentCommand.target());

        Gdx.app.log("TacticalGrid", GridUtilities.generateTacticalMap(gridManager, coordinateManager, currentTacticalGridId, currentActorMap));
    }

    private void createUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().left().pad(20);

        Label.LabelStyle titleStyle = new Label.LabelStyle(font, Color.CYAN);
        Label titleLabel = new Label("MILITARY COORDINATION - TOWER TRUST SYSTEM", titleStyle);
        mainTable.add(titleLabel).colspan(2).pad(10).row();

        Label.LabelStyle statusStyle = new Label.LabelStyle(font, Color.WHITE);
        Label statusLabel = new Label("Status: " + gameStatus, statusStyle);
        mainTable.add(statusLabel).pad(5).row();

        stage.addActor(mainTable);
    }

    private void startGameLoop() {
        gameLoop = Executors.newScheduledThreadPool(1);
        gameLoop.scheduleAtFixedRate(this::gameTick, 0, 5, TimeUnit.SECONDS);
    }

    private void gameTick() {
        // This runs on a separate thread
        tickCount++;

        if (currentCommand != null) {
            boolean hasTimedOut = CommandSystem.hasTimedOut(currentCommand);
            int cost = CommandSystem.calculateCost(currentCommand, 85, 20, 90);

            // Update game status (atomic operation due to volatile)
            gameStatus = String.format(
                "Tick %d: Command %s | Cost: %d | Timed out: %s",
                tickCount,
                currentCommand.type(),
                cost,
                hasTimedOut ? "YES" : "NO"
            );
        }

        Gdx.app.log("GameTick", gameStatus);
    }

    @Override
    public void render() {
        // This runs on the main thread
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background and HUD
        batch.begin();
        if (background != null) {
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }


        if (towerTexture != null) {
            tower.draw(batch, 1);
        }

        dGridDebugRenderer.render(batch, coordinateManager, GridUtilities.getGridById(gridManager, currentTacticalGridId), currentActorMap);
        dGridDebugRendererTowerSignal.render(batch, coordinateManager, GridUtilities.getGridById(gridManager, towerSignalGridId), towerSignalActorMap);

        // These reads are thread-safe due to volatile
        font.draw(batch, gameStatus, 20, Gdx.graphics.getHeight() - 100.0f);
        font.draw(batch, "Tick: " + tickCount, 20, Gdx.graphics.getHeight() - 130.0f);
        font.draw(batch, "Grid: " + GridUtilities.getGridById(gridManager, currentTacticalGridId).grid.rows() + "x" + GridUtilities.getGridById(gridManager, currentTacticalGridId).grid.cols(),
                  20, Gdx.graphics.getHeight() - 160.0f);
        font.draw(batch, "Press G for grid view, ESC to exit", 20, 50.0f);
        batch.end();

        // Update and draw UI
        stage.act();
        stage.draw();

        handleInput();
    }

    private void handleInput() {

        GridCoordinate target = null;

        // Add mouse click handling
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (!towerMoving) {
                target = mouseToGridCoordinate();
                moveTowerSmooth(currentTacticalGridId, target);
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.D)) {
            dGridDebugRenderer.setEnabled(!dGridDebugRenderer.isEnabled());
            Gdx.app.log("Debug", "Grid debug: " + (dGridDebugRenderer.isEnabled() ? "ON" : "OFF"));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.T)) {
            dGridDebugRendererTowerSignal.setEnabled(!dGridDebugRendererTowerSignal.isEnabled());
            Gdx.app.log("Debug", "Tower signal debug: " + (dGridDebugRendererTowerSignal.isEnabled() ? "ON" : "OFF"));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.G)) {
            // Thread-safe read of current grid state
            Gdx.app.log("TacticalMap", "\n" + GridUtilities.generateTacticalMap(gridManager,
                                                                                     coordinateManager,
                                                                                     currentTacticalGridId,
                                                                                     currentActorMap));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.M)) {
            if (!towerMoving) {
                moveTowerSmooth(currentTacticalGridId, target);
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.F)) {
            if (!towerMoving) {
                moveTowerInstant();
            }
        }

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            // Reset the game state
            Gdx.app.log("Game", "Switching grid id");
            // Switch to the tower signal grid
            UUID tmpGrid = towerSignalGridId;
            currentTacticalGridId = towerSignalGridId;
            towerSignalGridId = tmpGrid;
        }
    }

    private GridCoordinate mouseToGridCoordinate() {
    // Get mouse position in screen coordinates
    int mouseX = Gdx.input.getX();
    int mouseY = Gdx.input.getY();

    // Convert screen coordinates to world coordinates
    // Note: LibGDX screen Y is flipped (0 at top), so we need to convert
    Vector2 worldPos = new Vector2(mouseX, Gdx.graphics.getHeight() - mouseY);

    // Convert world position to grid coordinate
    GridCoordinate clickedGrid = GridUtilities.toGridCoordinate(currentTacticalGridId, coordinateManager, worldPos, CELL_SIZE, GRID_COLS);

    // Log the result
    Gdx.app.log("MouseClick", String.format("Clicked at screen(%d, %d) -> world(%.1f, %.1f) -> grid %s",
        mouseX, mouseY, worldPos.x, worldPos.y, clickedGrid.toGridReference()));

    // Check if the click is within the grid bounds
    if (GridUtilities.isValidGrid(GridUtilities.getGridById(gridManager, currentTacticalGridId).grid, clickedGrid)) {
        Gdx.app.log("MouseClick", "Valid grid cell: " + clickedGrid.toGridReference());
        // Do whatever you want with the clicked grid coordinate here
    } else {
        Gdx.app.log("MouseClick", "Clicked outside grid bounds");
    }

    return clickedGrid;
}

    private void moveTowerInstant() {
        GridCoordinate currentPos = GridUtilities.toGridCoordinate(currentTacticalGridId, coordinateManager, new Vector2(tower.getX(), tower.getY()), CELL_SIZE, GRID_COLS);
        GridCoordinate newPos = coordinateManager.coordinates.get(currentTacticalGridId)[(currentPos.id() + 1) % (GRID_ROWS * GRID_COLS)]; // Move to origin (0, 0)

        // Functional update - creates new grid state
        currentActorMap = GridUtilities.moveActor(
            currentActorMap,
            0, // Assuming tower ID is 0
            currentPos,
            newPos
        );

        // Update visual position
        Vector2 newWorldPos = GridUtilities.gridToWorld(GridUtilities.getGridById(gridManager, currentTacticalGridId), newPos);
        tower.setPosition(newWorldPos.x, newWorldPos.y);

        Gdx.app.log("Movement", "Tower moved to " + newPos.toGridReference());
        Gdx.app.log("Movement", "World position: " + new Vector2(tower.getX(), tower.getY()));
    }

    private void moveTowerSmooth(UUID gridId, final GridCoordinate targetPos) {
        GridCoordinate currentPos = GridUtilities.toGridCoordinate(gridId, coordinateManager, new Vector2(tower.getX(), tower.getY()), CELL_SIZE, GRID_COLS);

        GridCoordinate newPos = coordinateManager.coordinates.get(gridId)[(currentPos.id() + 1) % (GRID_ROWS * GRID_COLS)];
        if (targetPos == null) {
            Gdx.app.log("Movement", "No target position specified, using current position");
        } else {
            newPos = targetPos; // Use the specified target position
        }
        // Get the target world position
        Vector2 targetWorldPos = GridUtilities.gridToWorld(GridUtilities.getGridById(gridManager, gridId), newPos);

        // Set movement flag
        towerMoving = true;

        final GridCoordinate finalTargetPos = newPos;

        // Create smooth movement action
        tower.addAction(Actions.sequence(
            Actions.moveTo(targetWorldPos.x, targetWorldPos.y, MOVE_DURATION),
            Actions.run(() -> {
                // This runs when the movement is complete
                towerMoving = false;

                // Update the grid state (functional - creates new grid)
                currentActorMap = GridUtilities.moveActor(currentActorMap, 0, currentPos, finalTargetPos);

                Gdx.app.log("Movement", "Tower smoothly moved to " + finalTargetPos.toGridReference());
                Gdx.app.log("Movement", "Final position: " + new Vector2(tower.getX(), tower.getY()));
            })
        ));

        Gdx.app.log("Movement", "Starting smooth movement from " + currentPos.toGridReference() +
                   " to " + finalTargetPos.toGridReference());
    }

    @Override
    public void dispose() {
        if (gameLoop != null) {
            gameLoop.shutdown();
        }
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
        if (towerTexture != null) towerTexture.dispose();
        if (dGridDebugRenderer != null) dGridDebugRenderer.dispose();
        if (background != null) background.dispose();
    }
}
