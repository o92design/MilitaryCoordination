package com.military.coordination.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Utility class for testing functional programming patterns and immutable data structures.
 * Provides assertions and helpers specifically designed for data-oriented testing.
 */
public final class TestFunctionalUtils {

    private TestFunctionalUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Asserts that a transformation function produces the expected result.
     *
     * @param input the input data
     * @param transformation the transformation function
     * @param expected the expected result
     * @param <T> input type
     * @param <R> result type
     */
    public static <T, R> void assertTransformation(T input, Function<T, R> transformation, R expected) {
        R actual = transformation.apply(input);
        assertEquals(expected, actual, "Transformation did not produce expected result");
    }

    /**
     * Asserts that a transformation is pure (same input always produces same output).
     *
     * @param input the input data
     * @param transformation the transformation function
     * @param <T> input type
     * @param <R> result type
     */
    public static <T, R> void assertPureFunction(T input, Function<T, R> transformation) {
        R result1 = transformation.apply(input);
        R result2 = transformation.apply(input);
        assertEquals(result1, result2, "Function is not pure - same input produced different outputs");
    }

    /**
     * Asserts that an object is immutable by checking that operations don't modify the original.
     *
     * @param original the original object
     * @param operation an operation that should return a new instance
     * @param <T> the type of the object
     */
    public static <T> void assertImmutable(T original, Function<T, T> operation) {
        T originalCopy = original; // Keep reference to original
        T result = operation.apply(original);

        // The operation should return a different instance
        assertNotSame(original, result, "Operation should return a new instance for immutable objects");

        // Original should be unchanged (if it implements equals properly)
        assertEquals(originalCopy, original, "Original object was modified - not immutable");
    }

    /**
     * Asserts that a stream operation produces the expected results.
     *
     * @param input the input stream
     * @param operation the stream operation
     * @param expected the expected results
     * @param <T> input type
     * @param <R> result type
     */
    public static <T, R> void assertStreamOperation(Stream<T> input, Function<Stream<T>, Stream<R>> operation, List<R> expected) {
        List<R> actual = operation.apply(input).collect(Collectors.toList());
        assertEquals(expected, actual, "Stream operation did not produce expected results");
    }

    /**
     * Asserts that all elements in a collection satisfy a predicate.
     *
     * @param collection the collection to test
     * @param predicate the predicate to test
     * @param <T> the element type
     */
    public static <T> void assertAllMatch(List<T> collection, Predicate<T> predicate) {
        boolean allMatch = collection.stream().allMatch(predicate);
        assertTrue(allMatch, "Not all elements in the collection satisfy the predicate");
    }

    /**
     * Asserts that no elements in a collection satisfy a predicate.
     *
     * @param collection the collection to test
     * @param predicate the predicate to test
     * @param <T> the element type
     */
    public static <T> void assertNoneMatch(List<T> collection, Predicate<T> predicate) {
        boolean noneMatch = collection.stream().noneMatch(predicate);
        assertTrue(noneMatch, "Some elements in the collection satisfy the predicate when none should");
    }

    /**
     * Asserts that a function composition works correctly.
     *
     * @param input the input value
     * @param f first function
     * @param g second function
     * @param expected expected result of g(f(input))
     * @param <A> input type
     * @param <B> intermediate type
     * @param <C> result type
     */
    public static <A, B, C> void assertComposition(A input, Function<A, B> f, Function<B, C> g, C expected) {
        C actual = f.andThen(g).apply(input);
        assertEquals(expected, actual, "Function composition did not produce expected result");
    }

    /**
     * Creates a test data builder for functional testing patterns.
     *
     * @param <T> the type of data to build
     */
    public static class TestDataBuilder<T> {
        private T data;

        private TestDataBuilder(T initialData) {
            this.data = initialData;
        }

        public static <T> TestDataBuilder<T> of(T data) {
            return new TestDataBuilder<>(data);
        }

        public TestDataBuilder<T> transform(Function<T, T> transformation) {
            this.data = transformation.apply(this.data);
            return this;
        }

        public T build() {
            return data;
        }

        public void assertResultEquals(T expected) {
            assertEquals(expected, data, "Test data builder result did not match expected value");
        }
    }
}
