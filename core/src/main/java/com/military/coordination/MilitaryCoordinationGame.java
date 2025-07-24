package com.military.coordination;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.military.coordination.model.Command;
import com.military.coordination.model.CommandType;
import com.military.coordination.model.Priority;
import com.military.coordination.system.CommandSystem;

/**
 * Main game class for Military Coordination tactical simulation.
 * Implements the 5-second command tick system from the game design.
 */
public class MilitaryCoordinationGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Skin skin;

    // Game state
    private ScheduledExecutorService gameLoop;
    private int tickCount = 0;
    private Command currentCommand;
    private String gameStatus = "Initializing Tower Trust System...";

    @Override
    public void create() {
        // Initialize graphics
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.GREEN); // Military terminal style

        // Initialize UI
        stage = new Stage(new ScreenViewport());
        skin = new Skin();
        skin.add("default-font", font);

        // Create UI layout
        createUI();

        // Initialize game systems
        initializeGameSystems();

        // Start the 5-second game loop from your game design
        startGameLoop();

        Gdx.app.log("MilitaryCoordination", "Tower Trust System initialized");
    }

    private void createUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().left().pad(20);

        // Title
        Label.LabelStyle titleStyle = new Label.LabelStyle(font, Color.CYAN);
        Label titleLabel = new Label("MILITARY COORDINATION - TOWER TRUST SYSTEM", titleStyle);
        mainTable.add(titleLabel).colspan(2).pad(10).row();

        // Status display
        Label.LabelStyle statusStyle = new Label.LabelStyle(font, Color.WHITE);
        Label statusLabel = new Label("Status: " + gameStatus, statusStyle);
        mainTable.add(statusLabel).pad(5).row();

        stage.addActor(mainTable);
    }

    private void initializeGameSystems() {
        // Create a test command to demonstrate integration with your existing system
        currentCommand = new Command(
            UUID.randomUUID(),
            CommandType.RECONNAISSANCE,
            "GRID-17",
            Priority.NORMAL,
            Duration.ofMinutes(10)
        );

        gameStatus = "Command created: " + currentCommand.type() + " targeting " + currentCommand.target();
    }

    private void startGameLoop() {
        gameLoop = Executors.newScheduledThreadPool(1);

        // 5-second tick system from your game design
        gameLoop.scheduleAtFixedRate(this::gameTick, 0, 5, TimeUnit.SECONDS);
    }

    private void gameTick() {
        tickCount++;

        // Process commands using your existing CommandSystem
        if (currentCommand != null) {
            boolean hasTimedOut = CommandSystem.hasTimedOut(currentCommand);
            int cost = CommandSystem.calculateCost(currentCommand, 85, 20, 90); // Mock unit state

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
        // Clear screen with dark military-style background
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw UI
        stage.act();
        stage.draw();

        // Draw additional info
        batch.begin();
        font.draw(batch, gameStatus, 20, Gdx.graphics.getHeight() - 100.0f);
        font.draw(batch, "Tick: " + tickCount, 20, Gdx.graphics.getHeight() - 130.0f);
        font.draw(batch, "Press ESC to exit", 20, 50.0f);
        batch.end();

        // Simple exit condition
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        if (gameLoop != null) {
            gameLoop.shutdown();
        }
        batch.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
    }
}
