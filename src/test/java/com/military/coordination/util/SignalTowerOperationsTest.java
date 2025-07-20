package com.military.coordination.util;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.military.coordination.model.Position;
import com.military.coordination.model.SignalTower;
import com.military.coordination.model.Unit;
import com.military.coordination.model.UnitStatus;
import com.military.coordination.model.UnitType;

/**
 * Tests for the SignalTowerOperations utility class following functional programming principles.
 * Tests verify pure functions work correctly with immutable SignalTower objects.
 */
@DisplayName("SignalTowerOperations Utility")
class SignalTowerOperationsTest {

    @Nested
    @DisplayName("Transmission Range Checks")
    class TransmissionRangeChecks {

        @Test
        @DisplayName("Should detect position within transmission range")
        void shouldDetectPositionWithinRange() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                10.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var closePosition = new Position(5, 5); // distance = ~7.07

            boolean result = SignalTowerOperations.isWithinTransmissionRange(tower, closePosition);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Should detect position outside transmission range")
        void shouldDetectPositionOutsideRange() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                10.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var farPosition = new Position(15, 15); // distance = ~21.21

            boolean result = SignalTowerOperations.isWithinTransmissionRange(tower, farPosition);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Should detect unit within transmission range")
        void shouldDetectUnitWithinRange() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(10, 10),
                100,
                15.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var unit = new Unit(
                "UNIT-001",
                UnitType.RECONNAISSANCE,
                75,
                new Position(20, 10), // distance = 10
                UnitStatus.READY,
                Collections.emptySet()
            );

            boolean result = SignalTowerOperations.isUnitInRange(tower, unit);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Should validate null inputs for range checks")
        void shouldValidateNullInputsForRangeChecks() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                10.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var position = new Position(5, 5);

            assertThatThrownBy(() -> SignalTowerOperations.isWithinTransmissionRange(null, position))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Signal tower cannot be null");

            assertThatThrownBy(() -> SignalTowerOperations.isWithinTransmissionRange(tower, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Target position cannot be null");

            assertThatThrownBy(() -> SignalTowerOperations.isUnitInRange(tower, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Unit cannot be null");
        }
    }

    @Nested
    @DisplayName("Unit Filtering")
    class UnitFiltering {

        @Test
        @DisplayName("Should filter units within range")
        void shouldFilterUnitsWithinRange() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                10.0,
                Collections.emptyList(),
                Collections.emptyList()
            );

            var closeUnit = new Unit("CLOSE-001", UnitType.RECONNAISSANCE, 75, new Position(5, 0), UnitStatus.READY, Collections.emptySet());
            var farUnit = new Unit("FAR-001", UnitType.ASSAULT, 80, new Position(20, 0), UnitStatus.READY, Collections.emptySet());
            var units = List.of(closeUnit, farUnit);

            List<Unit> unitsInRange = SignalTowerOperations.getUnitsInRange(tower, units);

            assertThat(unitsInRange).hasSize(1);
            assertThat(unitsInRange.get(0).id()).isEqualTo("CLOSE-001");
        }

        @Test
        @DisplayName("Should return empty list when no units in range")
        void shouldReturnEmptyListWhenNoUnitsInRange() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                5.0,
                Collections.emptyList(),
                Collections.emptyList()
            );

            var farUnit1 = new Unit("FAR-001", UnitType.ASSAULT, 80, new Position(20, 0), UnitStatus.READY, Collections.emptySet());
            var farUnit2 = new Unit("FAR-002", UnitType.SUPPORT, 85, new Position(0, 20), UnitStatus.READY, Collections.emptySet());
            var units = List.of(farUnit1, farUnit2);

            List<Unit> unitsInRange = SignalTowerOperations.getUnitsInRange(tower, units);

            assertThat(unitsInRange).isEmpty();
        }
    }

    @Nested
    @DisplayName("Tower Range Overlap")
    class TowerRangeOverlap {

        @Test
        @DisplayName("Should detect overlapping transmission ranges")
        void shouldDetectOverlappingRanges() {
            var tower1 = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                10.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var tower2 = new SignalTower(
                "TOWER-002",
                new Position(15, 0), // distance = 15
                100,
                8.0, // combined range = 18 > 15
                Collections.emptyList(),
                Collections.emptyList()
            );

            boolean hasOverlap = SignalTowerOperations.hasOverlappingRange(tower1, tower2);

            assertThat(hasOverlap).isTrue();
        }

        @Test
        @DisplayName("Should detect non-overlapping transmission ranges")
        void shouldDetectNonOverlappingRanges() {
            var tower1 = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                5.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var tower2 = new SignalTower(
                "TOWER-002",
                new Position(20, 0), // distance = 20
                100,
                5.0, // combined range = 10 < 20
                Collections.emptyList(),
                Collections.emptyList()
            );

            boolean hasOverlap = SignalTowerOperations.hasOverlappingRange(tower1, tower2);

            assertThat(hasOverlap).isFalse();
        }
    }

    @Nested
    @DisplayName("Signal Strength Calculations")
    class SignalStrengthCalculations {

        @Test
        @DisplayName("Should calculate maximum signal strength at tower position")
        void shouldCalculateMaxSignalStrengthAtTower() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(10, 10),
                100,
                20.0,
                Collections.emptyList(),
                Collections.emptyList()
            );

            double strength = SignalTowerOperations.calculateEffectiveSignalStrength(tower, tower.position());

            assertThat(strength).isEqualTo(1.0);
        }

        @Test
        @DisplayName("Should calculate reduced signal strength at distance")
        void shouldCalculateReducedSignalStrengthAtDistance() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                10.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var halfwayPosition = new Position(5, 0); // distance = 5, half of range

            double strength = SignalTowerOperations.calculateEffectiveSignalStrength(tower, halfwayPosition);

            assertThat(strength).isEqualTo(0.55); // 1.0 - (5/10 * 0.9) = 0.55
        }

        @Test
        @DisplayName("Should return zero signal strength outside range")
        void shouldReturnZeroSignalStrengthOutsideRange() {
            var tower = new SignalTower(
                "TOWER-001",
                new Position(0, 0),
                100,
                10.0,
                Collections.emptyList(),
                Collections.emptyList()
            );
            var outsidePosition = new Position(20, 0); // distance = 20 > range

            double strength = SignalTowerOperations.calculateEffectiveSignalStrength(tower, outsidePosition);

            assertThat(strength).isEqualTo(0.0);
        }
    }

    @Nested
    @DisplayName("Optimal Positioning")
    class OptimalPositioning {

        @Test
        @DisplayName("Should find optimal position as centroid of target positions")
        void shouldFindOptimalPositionAsCentroid() {
            var positions = List.of(
                new Position(0, 0),
                new Position(10, 0),
                new Position(10, 10),
                new Position(0, 10)
            );

            Position optimal = SignalTowerOperations.findOptimalTowerPosition(positions);

            assertThat(optimal.x()).isEqualTo(5); // (0+10+10+0)/4 = 5
            assertThat(optimal.y()).isEqualTo(5); // (0+0+10+10)/4 = 5
        }

        @Test
        @DisplayName("Should validate non-empty position list")
        void shouldValidateNonEmptyPositionList() {
            assertThatThrownBy(() -> SignalTowerOperations.findOptimalTowerPosition(Collections.emptyList()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Target positions cannot be null or empty");

            assertThatThrownBy(() -> SignalTowerOperations.findOptimalTowerPosition(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Target positions cannot be null or empty");
        }
    }
}
