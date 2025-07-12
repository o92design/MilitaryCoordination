package com.military.coordination.util;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Utility class for testing game loop and real-time operations.
 * Provides tools for testing timing, frame rates, and game state updates.
 */
public final class TestGameLoopUtils {

    private TestGameLoopUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Simulates a simple game loop for testing purposes.
     */
    public static class MockGameLoop {
        private boolean running = false;
        private final List<Duration> frameTimes = new ArrayList<>();
        private final AtomicInteger frameCount = new AtomicInteger(0);
        private Duration targetFrameTime = Duration.ofMillis(16); // ~60 FPS

        public void setTargetFPS(int fps) {
            this.targetFrameTime = Duration.ofMillis(1000 / fps);
        }

        public void runFor(Duration duration, Runnable updateLogic) {
            running = true;
            Instant startTime = Instant.now();
            Instant endTime = startTime.plus(duration);

            while (running && Instant.now().isBefore(endTime)) {
                Instant frameStart = Instant.now();

                updateLogic.run();
                frameCount.incrementAndGet();

                // Add a small delay to ensure measurable frame time
                try {
                    Thread.sleep(1); // 1ms minimum frame time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                Duration frameTime = Duration.between(frameStart, Instant.now());
                frameTimes.add(frameTime);

                // Simple frame rate limiting using spin-wait for testing
                Duration remainingTime = targetFrameTime.minus(frameTime);
                if (!remainingTime.isNegative() && remainingTime.toNanos() > 0) {
                    long spinEndTime = System.nanoTime() + remainingTime.toNanos();
                    while (System.nanoTime() < spinEndTime) {
                        Thread.onSpinWait(); // More efficient than sleep for short waits
                    }
                }
            }
            running = false;
        }

        public void stop() {
            running = false;
        }

        public int getFrameCount() {
            return frameCount.get();
        }

        public List<Duration> getFrameTimes() {
            return List.copyOf(frameTimes);
        }

        public double getAverageFrameTime() {
            return frameTimes.stream()
                .mapToLong(Duration::toNanos)
                .average()
                .orElse(0.0) / 1_000_000.0; // Convert to milliseconds
        }

        public boolean isRunning() {
            return running;
        }
    }

    /**
     * Tracks state changes over time for testing purposes.
     */
    public static class StateTracker<T> {
        private final List<StateSnapshot<T>> snapshots = new ArrayList<>();

        public void recordState(T state) {
            snapshots.add(new StateSnapshot<>(Instant.now(), state));
        }

        public List<StateSnapshot<T>> getSnapshots() {
            return List.copyOf(snapshots);
        }

        public int getSnapshotCount() {
            return snapshots.size();
        }

        public T getFirstState() {
            return snapshots.isEmpty() ? null : snapshots.get(0).state();
        }

        public T getLastState() {
            return snapshots.isEmpty() ? null : snapshots.get(snapshots.size() - 1).state();
        }

        public Duration getTotalDuration() {
            if (snapshots.size() < 2) {
                return Duration.ZERO;
            }
            return Duration.between(snapshots.get(0).timestamp(),
                                  snapshots.get(snapshots.size() - 1).timestamp());
        }
    }

    /**
     * Represents a snapshot of state at a specific time.
     */
    public record StateSnapshot<T>(Instant timestamp, T state) {}

    /**
     * Asserts that a game loop maintains a target frame rate within tolerance.
     */
    public static void assertFrameRate(MockGameLoop gameLoop, int expectedFPS, double tolerancePercent) {
        double expectedFrameTime = 1000.0 / expectedFPS; // milliseconds
        double actualFrameTime = gameLoop.getAverageFrameTime();
        double tolerance = expectedFrameTime * (tolerancePercent / 100.0);

        assertTrue(Math.abs(actualFrameTime - expectedFrameTime) <= tolerance,
            String.format("Frame rate outside tolerance. Expected: %.2fms, Actual: %.2fms, Tolerance: %.2fms",
                expectedFrameTime, actualFrameTime, tolerance));
    }

    /**
     * Asserts that state updates occur at regular intervals.
     */
    public static <T> void assertRegularStateUpdates(StateTracker<T> tracker, Duration expectedInterval, double tolerancePercent) {
        List<StateSnapshot<T>> snapshots = tracker.getSnapshots();
        assertTrue(snapshots.size() >= 2, "Need at least 2 state snapshots to test intervals");

        for (int i = 1; i < snapshots.size(); i++) {
            Duration actualInterval = Duration.between(snapshots.get(i-1).timestamp(), snapshots.get(i).timestamp());
            long expectedNanos = expectedInterval.toNanos();
            long actualNanos = actualInterval.toNanos();
            long tolerance = (long) (expectedNanos * (tolerancePercent / 100.0));

            assertTrue(Math.abs(actualNanos - expectedNanos) <= tolerance,
                String.format("State update interval outside tolerance at snapshot %d. Expected: %dms, Actual: %dms",
                    i, expectedInterval.toMillis(), actualInterval.toMillis()));
        }
    }

    /**
     * Asserts that state changes occur as expected over time.
     */
    public static <T> void assertStateProgression(StateTracker<T> tracker, Consumer<List<StateSnapshot<T>>> assertions) {
        assertions.accept(tracker.getSnapshots());
    }

    /**
     * Creates a test scenario that runs for a specific duration and validates the results.
     */
    public static void runTestScenario(Duration duration, Runnable gameLogic, Consumer<MockGameLoop> validation) {
        MockGameLoop gameLoop = new MockGameLoop();
        gameLoop.runFor(duration, gameLogic);
        validation.accept(gameLoop);
    }

    /**
     * Tests that a function can maintain performance under load.
     */
    public static void assertPerformanceUnderLoad(Runnable operation, int iterations, Duration maxExecutionTime) {
        Instant start = Instant.now();

        for (int i = 0; i < iterations; i++) {
            operation.run();
        }

        Duration totalTime = Duration.between(start, Instant.now());
        assertTrue(totalTime.compareTo(maxExecutionTime) <= 0,
            String.format("Performance test failed. %d iterations took %dms, expected maximum %dms",
                iterations, totalTime.toMillis(), maxExecutionTime.toMillis()));
    }
}
