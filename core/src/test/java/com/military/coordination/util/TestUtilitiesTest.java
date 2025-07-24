package com.military.coordination.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the test utilities to ensure they work correctly.
 * This is a meta-test that validates our testing framework extensions.
 */
@DisplayName("Test Utilities Validation")
class TestUtilitiesTest {

    @Test
    @DisplayName("Timing utilities should measure execution time accurately")
    void timingUtilitiesShouldWork() {
        // Test timing a simple operation
        var result = TestTimingUtils.timeExecution(() -> {
            // Simulate some work
            int sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum += i;
            }
            return sum;
        });

        assertNotNull(result);
        assertTrue(result.getExecutionTimeMillis() >= 0);
        assertEquals(499500, result.result()); // 0+1+2+...+999 = 999*1000/2 = 499500
    }

    @Test
    @DisplayName("Functional utilities should validate pure functions")
    void functionalUtilitiesShouldValidatePurity() {
        Function<Integer, Integer> pureFunction = x -> x * 2;

        // This should pass - pure function
        assertDoesNotThrow(() ->
            TestFunctionalUtils.assertPureFunction(5, pureFunction)
        );
    }

    @Test
    @DisplayName("Functional utilities should detect composition")
    void functionalUtilitiesShouldTestComposition() {
        Function<Integer, Integer> addOne = x -> x + 1;
        Function<Integer, Integer> multiplyTwo = x -> x * 2;

        // Test that (5 + 1) * 2 = 12
        TestFunctionalUtils.assertComposition(5, addOne, multiplyTwo, 12);
    }

    @Test
    @DisplayName("Game loop utilities should simulate loops")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void gameLoopUtilitiesShouldWork() {
        var gameLoop = new TestGameLoopUtils.MockGameLoop();
        gameLoop.setTargetFPS(10); // Low FPS for quick testing

        // Run for a short duration
        gameLoop.runFor(Duration.ofMillis(100), () -> {
            // Simple game logic - just count frames
        });

        assertTrue(gameLoop.getFrameCount() > 0);
        assertFalse(gameLoop.isRunning());
        assertTrue(gameLoop.getAverageFrameTime() > 0);
    }

    @Test
    @DisplayName("State tracker should record state changes")
    void stateTrackerShouldWork() {
        var tracker = new TestGameLoopUtils.StateTracker<Integer>();

        // Record some state changes with small delays to ensure different timestamps
        tracker.recordState(1);
        try {
            Thread.sleep(1); // Small delay to ensure different timestamp
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        tracker.recordState(2);
        try {
            Thread.sleep(1); // Small delay to ensure different timestamp
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        tracker.recordState(3);

        assertEquals(3, tracker.getSnapshotCount());
        assertEquals(Integer.valueOf(1), tracker.getFirstState());
        assertEquals(Integer.valueOf(3), tracker.getLastState());
        assertTrue(tracker.getTotalDuration().toNanos() > 0);
    }

    @Test
    @DisplayName("Test data builder should work with transformations")
    void testDataBuilderShouldWork() {
        String result = TestFunctionalUtils.TestDataBuilder
            .of("hello")
            .transform(s -> s.toUpperCase())
            .transform(s -> s + " WORLD")
            .build();

        assertEquals("HELLO WORLD", result);
    }

    @Test
    @DisplayName("Performance testing should validate execution time")
    void performanceTestingShouldWork() {
        // Test that a simple operation completes within reasonable time
        assertDoesNotThrow(() ->
            TestGameLoopUtils.assertPerformanceUnderLoad(
                () -> Math.sqrt(123.456), // Simple math operation
                1000, // 1000 iterations
                Duration.ofMillis(100) // Should complete in 100ms
            )
        );
    }

    @Test
    @DisplayName("Wait for condition should work with timeouts")
    void waitForConditionShouldWork() {
        // Test a condition that becomes true immediately
        boolean result = TestTimingUtils.waitForCondition(
            () -> true,
            100,
            TimeUnit.MILLISECONDS
        );
        assertTrue(result);

        // Test a condition that never becomes true
        boolean failResult = TestTimingUtils.waitForCondition(
            () -> false,
            10,
            TimeUnit.MILLISECONDS
        );
        assertFalse(failResult);
    }

    @Test
    @DisplayName("Stream operations testing should work")
    void streamOperationsTestingShouldWork() {
        List<Integer> input = List.of(1, 2, 3, 4, 5);
        List<Integer> expected = List.of(2, 4, 6, 8, 10);

        TestFunctionalUtils.assertStreamOperation(
            input.stream(),
            stream -> stream.map(x -> x * 2),
            expected
        );
    }

    @Test
    @DisplayName("Collection predicate testing should work")
    void collectionPredicateTestingShouldWork() {
        List<Integer> evenNumbers = List.of(2, 4, 6, 8);

        // All should be even
        TestFunctionalUtils.assertAllMatch(evenNumbers, x -> x % 2 == 0);

        // None should be negative
        TestFunctionalUtils.assertNoneMatch(evenNumbers, x -> x < 0);
    }
}
