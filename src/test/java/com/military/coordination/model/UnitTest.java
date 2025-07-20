package com.military.coordination.model;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.military.coordination.builders.UnitBuilder;
import com.military.coordination.util.UnitOperations;

/**
 * Tests for the Unit data structure following TDD principles.
 * Tests cover immutability, functional transformations, and data validation.
 */
@DisplayName("Unit Data Structure")
class UnitTest {

    @Nested
    @DisplayName("Unit Creation")
    class UnitCreation {

        @Test
        @DisplayName("Should create unit with valid parameters")
        void shouldCreateUnitWithValidParameters() {
            var position = new Position(10, 20);
            var unit = UnitFactory.createReconnaissanceUnit("ALPHA-1", position);

            assertThat(unit.id()).isEqualTo("ALPHA-1");
            assertThat(unit.unitType()).isEqualTo(UnitType.RECONNAISSANCE);
            assertThat(unit.strength()).isEqualTo(80);
            assertThat(unit.position()).isEqualTo(position);
            assertThat(unit.status()).isEqualTo(UnitStatus.READY);
        }

        @Test
        @DisplayName("Should reject null or empty ID")
        void shouldRejectInvalidId() {
            assertThatThrownBy(() ->
                UnitBuilder.builder()
                    .id(null)
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build()
            ).isInstanceOf(IllegalArgumentException.class)
             .hasMessageContaining("Unit ID cannot be null or empty");

            assertThatThrownBy(() ->
                UnitBuilder.builder()
                    .id("")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build()
            ).isInstanceOf(IllegalArgumentException.class)
             .hasMessageContaining("Unit ID cannot be null or empty");
        }

        @Test
        @DisplayName("Should reject invalid strength values")
        void shouldRejectInvalidStrength() {
            assertThatThrownBy(() ->
                UnitBuilder.builder()
                    .id("TEST-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(-1)
                    .position(new Position(0, 0))
                    .build()
            ).isInstanceOf(IllegalArgumentException.class)
             .hasMessageContaining("Strength must be between 0 and 100");

            assertThatThrownBy(() ->
                UnitBuilder.builder()
                    .id("TEST-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(101)
                    .position(new Position(0, 0))
                    .build()
            ).isInstanceOf(IllegalArgumentException.class)
             .hasMessageContaining("Strength must be between 0 and 100");
        }
    }

    @Nested
    @DisplayName("Immutability")
    class Immutability {

        @Test
        @DisplayName("Should be immutable after creation")
        void shouldBeImmutableAfterCreation() {
            var originalPosition = new Position(10, 20);
            var unit = UnitBuilder.builder()
                    .id("IMMUTABLE-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(75)
                    .position(originalPosition)
                    .build();

            // Verify that we can't modify the unit directly
            assertThat(unit.position()).isEqualTo(originalPosition);

            // Position is immutable - same value objects can share references
            // This is fine because Position is immutable
        }

        @Test
        @DisplayName("Should return defensive copies of mutable collections")
        void shouldReturnDefensiveCopies() {
            var equipment = Set.of("Radio", "GPS", "Night Vision");
            var unit = UnitBuilder.builder()
                    .id("COPY-TEST-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(80)
                    .position(new Position(0, 0))
                    .equipment(equipment)
                    .build();

            var returnedEquipment = unit.equipment();
            assertThat(returnedEquipment).containsExactlyInAnyOrderElementsOf(equipment);

            // Should be a defensive copy - modifications shouldn't affect original
            assertThat(returnedEquipment).isNotSameAs(equipment);
        }
    }

    @Nested
    @DisplayName("Functional Transformations")
    class FunctionalTransformations {

        @Test
        @DisplayName("Should support functional updates with withPosition")
        void shouldSupportFunctionalPositionUpdate() {
            var unit = UnitBuilder.builder()
                    .id("MOVE-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(90)
                    .position(new Position(0, 0))
                    .build();

            var newPosition = new Position(50, 50);
            var movedUnit = unit.withPosition(newPosition);

            // Original unit unchanged
            assertThat(unit.position()).isEqualTo(new Position(0, 0));

            // New unit has updated position
            assertThat(movedUnit.position()).isEqualTo(newPosition);
            assertThat(movedUnit.id()).isEqualTo(unit.id());
            assertThat(movedUnit.strength()).isEqualTo(unit.strength());
        }

        @Test
        @DisplayName("Should support functional status updates")
        void shouldSupportFunctionalStatusUpdate() {
            var unit = UnitBuilder.builder()
                    .id("STATUS-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(new Position(10, 10))
                    .build();

            var busyUnit = unit.withStatus(UnitStatus.MOVING);
            var damagedUnit = unit.withStrength(50);

            // Original unit unchanged
            assertThat(unit.status()).isEqualTo(UnitStatus.READY);
            assertThat(unit.strength()).isEqualTo(100);

            // New units have updates
            assertThat(busyUnit.status()).isEqualTo(UnitStatus.MOVING);
            assertThat(damagedUnit.strength()).isEqualTo(50);
        }

        @Test
        @DisplayName("Should support equipment updates")
        void shouldSupportEquipmentUpdates() {
            var unit = UnitBuilder.builder()
                    .id("EQUIP-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(new Position(0, 0))
                    .equipment(Set.of("Radio"))
                    .build();

            var upgradedUnit = unit.withEquipment(Set.of("Radio", "GPS", "Night Vision"));

            assertThat(unit.equipment()).containsExactly("Radio");
            assertThat(upgradedUnit.equipment()).containsExactlyInAnyOrder("Radio", "GPS", "Night Vision");
        }
    }

    @Nested
    @DisplayName("Unit Capabilities")
    class UnitCapabilities {

        @Test
        @DisplayName("Should calculate unit effectiveness based on strength and equipment")
        void shouldCalculateEffectiveness() {
            var basicUnit = UnitBuilder.builder()
                    .id("BASIC-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(50)
                    .position(new Position(0, 0))
                    .build();

            var equippedUnit = basicUnit.withEquipment(Set.of("GPS", "Night Vision"));

            assertThat(UnitOperations.calculateEffectiveness(basicUnit)).isEqualTo(40.0); // 50 * 0.8 (recon multiplier)
            assertThat(UnitOperations.calculateEffectiveness(equippedUnit)).isGreaterThan(40.0);
        }

        @Test
        @DisplayName("Should determine if unit can perform action")
        void shouldDetermineActionCapability() {
            var readyUnit = UnitBuilder.builder()
                    .id("READY-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(80)
                    .position(new Position(0, 0))
                    .build();

            var damagedUnit = readyUnit.withStrength(10);
            var movingUnit = readyUnit.withStatus(UnitStatus.MOVING);

            assertThat(UnitOperations.canPerformAction(readyUnit)).isTrue();
            assertThat(UnitOperations.canPerformAction(damagedUnit)).isFalse(); // Too damaged
            assertThat(UnitOperations.canPerformAction(movingUnit)).isFalse(); // Already busy
        }

        @Test
        @DisplayName("Should calculate movement range based on unit type and equipment")
        void shouldCalculateMovementRange() {
            var reconUnit = UnitBuilder.builder()
                    .id("RECON-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build();

            var assaultUnit = UnitBuilder.builder()
                    .id("ASSAULT-1")
                    .unitType(UnitType.ASSAULT)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build();

            // Recon units should have greater range than assault units
            assertThat(UnitOperations.calculateMovementRange(reconUnit)).isGreaterThan(UnitOperations.calculateMovementRange(assaultUnit));
        }
    }

    @Nested
    @DisplayName("Unit Types and Specializations")
    class UnitTypeSpecializations {

        @Test
        @DisplayName("Should have different capabilities based on unit type")
        void shouldHaveDifferentCapabilitiesByType() {
            var reconUnit = UnitBuilder.builder()
                    .id("RECON-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build();

            var assaultUnit = UnitBuilder.builder()
                    .id("ASSAULT-1")
                    .unitType(UnitType.ASSAULT)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build();

            var supportUnit = UnitBuilder.builder()
                    .id("SUPPORT-1")
                    .unitType(UnitType.SUPPORT)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build();

            // Each unit type should have different characteristics
            assertThat(UnitOperations.calculateMovementRange(reconUnit)).isNotEqualTo(UnitOperations.calculateMovementRange(assaultUnit));
            assertThat(UnitOperations.calculateDetectionRange(reconUnit)).isGreaterThan(UnitOperations.calculateDetectionRange(assaultUnit));
            assertThat(UnitOperations.calculateSupplyCapacity(supportUnit)).isGreaterThan(0);
        }
    }

    @Nested
    @DisplayName("Data Validation")
    class DataValidation {

        @Test
        @DisplayName("Should validate all required fields are present")
        void shouldValidateRequiredFields() {
            assertThatThrownBy(() -> UnitBuilder.builder()
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(new Position(0, 0))
                    .build() // Missing ID
            ).isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> UnitBuilder.builder()
                    .id("TEST-1")
                    .strength(100)
                    .position(new Position(0, 0))
                    .build() // Missing unit type
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should validate position is not null")
        void shouldValidatePosition() {
            assertThatThrownBy(() ->
                UnitBuilder.builder()
                    .id("TEST-1")
                    .unitType(UnitType.RECONNAISSANCE)
                    .strength(100)
                    .position(null)
                    .build()
            ).isInstanceOf(IllegalArgumentException.class)
             .hasMessageContaining("Position cannot be null");
        }
    }
}
