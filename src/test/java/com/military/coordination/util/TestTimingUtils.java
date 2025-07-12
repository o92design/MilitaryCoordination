package com.military.coordination.util;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Utility class for testing performance and timing-related functionality.
 * Useful for testing the real-time game loop and performance-critical operations.
 */
public final class TestTimingUtils {

    private TestTimingUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Measures the execution time of a supplier function.
     *
     * @param supplier the function to time
     * @param <T> the return type of the supplier
     * @return a TimedResult containing the result and execution time
     */
    public static <T> TimedResult<T> timeExecution(Supplier<T> supplier) {
        Instant start = Instant.now();
        T result = supplier.get();
        Duration duration = Duration.between(start, Instant.now());
        return new TimedResult<>(result, duration);
    }

    /**
     * Measures the execution time of a runnable.
     *
     * @param runnable the code to time
     * @return the execution duration
     */
    public static Duration timeExecution(Runnable runnable) {
        Instant start = Instant.now();
        runnable.run();
        return Duration.between(start, Instant.now());
    }

    /**
     * Waits for a condition to become true within a timeout period.
     * Useful for testing asynchronous operations.
     *
     * @param condition the condition to check
     * @param timeout the maximum time to wait
     * @param unit the time unit
     * @return true if the condition became true within the timeout, false otherwise
     */
    public static boolean waitForCondition(Supplier<Boolean> condition, long timeout, TimeUnit unit) {
        long timeoutNanos = unit.toNanos(timeout);
        long startTime = System.nanoTime();

        while (System.nanoTime() - startTime < timeoutNanos) {
            if (condition.get()) {
                return true;
            }
            // Use Thread.onSpinWait() for better performance and less CPU usage
            Thread.onSpinWait();
        }
        return false;
    }

    /**
     * Executes a task asynchronously and returns a CompletableFuture.
     * Useful for testing concurrent operations.
     *
     * @param task the task to execute
     * @return a CompletableFuture representing the async execution
     */
    public static CompletableFuture<Void> executeAsync(Runnable task) {
        return CompletableFuture.runAsync(task);
    }

    /**
     * Result container for timed operations.
     *
     * @param <T> the type of the result
     */
    public record TimedResult<T>(T result, Duration executionTime) {

        /**
         * Returns true if the execution took longer than the specified duration.
         *
         * @param threshold the time threshold
         * @return true if execution time exceeded the threshold
         */
        public boolean exceededTime(Duration threshold) {
            return executionTime.compareTo(threshold) > 0;
        }

        /**
         * Returns the execution time in milliseconds.
         *
         * @return execution time in milliseconds
         */
        public long getExecutionTimeMillis() {
            return executionTime.toMillis();
        }
    }
}
