package com.military.coordination.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.military.coordination.model.Position;

/**
 * Tests for the PositionOperations utility class following functional programming principles.
 * Tests verify pure functions work correctly with immutable Position objects.
 */
@DisplayName("PositionOperations Utility")
class PositionOperationsTest {

    @Nested
    @DisplayName("Distance Calculations")
    class DistanceCalculations {

        @Test
        @DisplayName("Should calculate Euclidean distance correctly")
        void shouldCalculateEuclideanDistance() {
            var origin = new Position(0, 0);
            var point = new Position(3, 4);

            double distance = PositionOperations.calculateDistance(origin, point);

            assertThat(distance).isEqualTo(5.0); // 3-4-5 triangle
        }

        @Test
        @DisplayName("Should calculate Manhattan distance correctly")
        void shouldCalculateManhattanDistance() {
            var from = new Position(1, 1);
            var to = new Position(4, 5);

            int distance = PositionOperations.calculateManhattanDistance(from, to);

            assertThat(distance).isEqualTo(7); // |4-1| + |5-1| = 3 + 4 = 7
        }

        @Test
        @DisplayName("Should handle same position distance")
        void shouldHandleSamePositionDistance() {
            var position = new Position(10, 20);

            double euclidean = PositionOperations.calculateDistance(position, position);
            int manhattan = PositionOperations.calculateManhattanDistance(position, position);

            assertThat(euclidean).isZero();
            assertThat(manhattan).isZero();
        }

        @Test
        @DisplayName("Should throw exception for null positions in distance calculations")
        void shouldThrowExceptionForNullPositionsInDistance() {
            var position = new Position(0, 0);

            assertThatThrownBy(() -> PositionOperations.calculateDistance(null, position))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Positions cannot be null");

            assertThatThrownBy(() -> PositionOperations.calculateDistance(position, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Positions cannot be null");

            assertThatThrownBy(() -> PositionOperations.calculateManhattanDistance(null, position))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Positions cannot be null");
        }
    }

    @Nested
    @DisplayName("Position Creation")
    class PositionCreation {

        @Test
        @DisplayName("Should create offset position correctly")
        void shouldCreateOffsetPosition() {
            var original = new Position(10, 20);
            var offset = PositionOperations.createOffset(original, 5, -3);

            assertThat(offset.x()).isEqualTo(15);
            assertThat(offset.y()).isEqualTo(17);

            // Original should be unchanged (immutability)
            assertThat(original.x()).isEqualTo(10);
            assertThat(original.y()).isEqualTo(20);
        }

        @Test
        @DisplayName("Should throw exception for null position in offset")
        void shouldThrowExceptionForNullPositionInOffset() {
            assertThatThrownBy(() -> PositionOperations.createOffset(null, 1, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Position cannot be null");
        }
    }

    @Nested
    @DisplayName("Range Checking")
    class RangeChecking {

        @Test
        @DisplayName("Should determine if positions are within range")
        void shouldDetermineIfWithinRange() {
            var center = new Position(0, 0);
            var close = new Position(3, 4); // Distance = 5
            var far = new Position(10, 10); // Distance ≈ 14.14

            assertThat(PositionOperations.isWithinRange(center, close, 5.0)).isTrue();
            assertThat(PositionOperations.isWithinRange(center, close, 6.0)).isTrue();
            assertThat(PositionOperations.isWithinRange(center, close, 4.0)).isFalse();
            assertThat(PositionOperations.isWithinRange(center, far, 10.0)).isFalse();
            assertThat(PositionOperations.isWithinRange(center, far, 15.0)).isTrue();
        }

        @Test
        @DisplayName("Should handle edge case of exact range boundary")
        void shouldHandleExactRangeBoundary() {
            var from = new Position(0, 0);
            var to = new Position(3, 4); // Distance = 5.0

            assertThat(PositionOperations.isWithinRange(from, to, 5.0)).isTrue();
        }

        @Test
        @DisplayName("Should throw exception for invalid range parameters")
        void shouldThrowExceptionForInvalidRangeParameters() {
            var position = new Position(0, 0);

            assertThatThrownBy(() -> PositionOperations.isWithinRange(null, position, 1.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Positions cannot be null");

            assertThatThrownBy(() -> PositionOperations.isWithinRange(position, null, 1.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Positions cannot be null");

            assertThatThrownBy(() -> PositionOperations.isWithinRange(position, position, -1.0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Range cannot be negative");
        }
    }

    @Nested
    @DisplayName("Pure Function Properties")
    class PureFunctionProperties {

        @Test
        @DisplayName("Should be referentially transparent")
        void shouldBeReferentiallyTransparent() {
            var pos1 = new Position(1, 2);
            var pos2 = new Position(4, 6);

            // Same inputs should always produce same outputs
            double distance1 = PositionOperations.calculateDistance(pos1, pos2);
            double distance2 = PositionOperations.calculateDistance(pos1, pos2);

            assertThat(distance1).isEqualTo(distance2);

            // Function calls should not affect the input objects
            assertThat(pos1.x()).isEqualTo(1);
            assertThat(pos1.y()).isEqualTo(2);
            assertThat(pos2.x()).isEqualTo(4);
            assertThat(pos2.y()).isEqualTo(6);
        }
    }
}
