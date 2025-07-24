package com.military.coordination;

import java.time.Duration;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.military.coordination.model.Command;
import com.military.coordination.model.CommandType;
import com.military.coordination.model.Priority;
import com.military.coordination.system.CommandSystem;

/**
 * Console-based Military Coordination game.
 * Demonstrates your Command system with 5-second tick game loop.
 * This will be upgraded to LibGDX graphics once dependencies are resolved.
 */
public class ConsoleGame {

    private ScheduledExecutorService gameLoop;
    private int tickCount = 0;
    private Command currentCommand;
    private boolean running = true;

    public static void main(String[] args) {
        new ConsoleGame().start();
    }

    public void start() {
        System.out.println("=================================");
        System.out.println("MILITARY COORDINATION");
        System.out.println("Tower Trust System™ - Console Demo");
        System.out.println("=================================");
        System.out.println();

        // Create a demo command using your existing system
        currentCommand = new Command(
            UUID.randomUUID(),
            CommandType.RECONNAISSANCE,
            "GRID-17",
            Priority.NORMAL,
            Duration.ofMinutes(10)
        );

        System.out.println("Command created: " + currentCommand.type() + " targeting " + currentCommand.target());
        System.out.println("Command ID: " + currentCommand.id());
        System.out.println();

        // Start the 5-second game loop from your game design
        startGameLoop();

        // Simple input loop
        try (
            Scanner scanner = new Scanner(System.in)) {
            System.out.println("Game loop started! Press ENTER to stop...");
            scanner.nextLine();

            stop();
        }
    }

    private void startGameLoop() {
        gameLoop = Executors.newScheduledThreadPool(1);

        // 5-second tick system from your GAME_DESIGN.MD
        gameLoop.scheduleAtFixedRate(this::gameTick, 0, 5, TimeUnit.SECONDS);

        System.out.println("⏰ Game loop started - 5 second ticks");
        System.out.println();
    }

    private void gameTick() {
        if (!running) return;

        tickCount++;

        // Process commands using your existing CommandSystem
        if (currentCommand != null) {
            boolean hasTimedOut = CommandSystem.hasTimedOut(currentCommand);
            int cost = CommandSystem.calculateCost(currentCommand, 85, 20, 90); // Mock unit state
            boolean isUrgent = CommandSystem.isUrgent(currentCommand);
            int priorityScore = CommandSystem.getPriorityScore(currentCommand);

            System.out.printf(
                "🎮 TICK %d | Command: %s | Target: %s | Cost: %d | Priority: %d | Urgent: %s | Timed out: %s%n",
                tickCount,
                currentCommand.type(),
                currentCommand.target(),
                cost,
                priorityScore,
                isUrgent ? "YES" : "NO",
                hasTimedOut ? "YES" : "NO"
            );

            // Demonstrate functional programming - create new command with updated status
            if (tickCount == 3) {
                currentCommand = currentCommand.withStatus(com.military.coordination.model.CommandStatus.EXECUTING);
                System.out.println("  → Command status updated to EXECUTING");
            }

            if (tickCount == 6) {
                currentCommand = currentCommand.withStatus(com.military.coordination.model.CommandStatus.COMPLETED);
                System.out.println("  → Command status updated to COMPLETED");
                System.out.println("  ✅ Mission accomplished!");
            }
        }
    }

    private void stop() {
        running = false;
        if (gameLoop != null) {
            gameLoop.shutdown();
        }
        System.out.println();
        System.out.println("🛑 Game loop stopped");
        System.out.println("Tower Trust System offline");
    }
}
