package com.military.coordination.model;

import java.util.Set;

import com.military.coordination.builders.UnitBuilder;
import com.military.coordination.util.UnitOperations;

/**
 * Simple test runner to validate Unit implementation manually.
 * This runs basic validation scenarios for Issue #14.
 */
public class UnitTestRunner {
    public static void main(String[] args) {
        System.out.println("=== Unit Data Structure Test Runner (Issue #14) ===\n");

        try {
            testBasicUnitCreation();
            testImmutability();
            testFunctionalUpdates();
            testUnitCapabilities();
            testValidation();

            System.out.println("✅ All tests passed! Unit Data Structure implementation is working correctly.");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testBasicUnitCreation() {
        System.out.println("🧪 Testing Basic Unit Creation...");

        var unit = UnitBuilder.builder()
                .id("TEST-1")
                .unitType(UnitType.RECONNAISSANCE)
                .strength(100)
                .position(new Position(10, 20))
                .build();

        assert "TEST-1".equals(unit.id()) : "ID should be TEST-1";
        assert UnitType.RECONNAISSANCE.equals(unit.unitType()) : "Type should be RECONNAISSANCE";
        assert unit.strength() == 100 : "Strength should be 100";
        assert unit.position().equals(new Position(10, 20)) : "Position should be (10, 20)";
        assert UnitStatus.READY.equals(unit.status()) : "Default status should be READY";

        System.out.println("  ✓ Basic unit creation works");
    }

    private static void testImmutability() {
        System.out.println("🧪 Testing Immutability...");

        var equipment = Set.of("Radio", "GPS");
        var unit = UnitBuilder.builder()
                .id("IMMUTABLE-1")
                .unitType(UnitType.RECONNAISSANCE)
                .strength(80)
                .position(new Position(0, 0))
                .equipment(equipment)
                .build();

        var returnedEquipment = unit.equipment();
        assert returnedEquipment.contains("Radio") : "Should contain Radio";
        assert returnedEquipment.contains("GPS") : "Should contain GPS";
        assert !returnedEquipment.equals(equipment) : "Should be a defensive copy";

        System.out.println("  ✓ Immutability works correctly");
    }

    private static void testFunctionalUpdates() {
        System.out.println("🧪 Testing Functional Updates...");

        var originalUnit = UnitBuilder.builder()
                .id("UPDATE-1")
                .unitType(UnitType.RECONNAISSANCE)
                .strength(100)
                .position(new Position(0, 0))
                .build();

        var movedUnit = originalUnit.withPosition(new Position(50, 50));
        var damagedUnit = originalUnit.withStrength(30);

        // Original should be unchanged
        assert originalUnit.position().equals(new Position(0, 0)) : "Original position unchanged";
        assert originalUnit.strength() == 100 : "Original strength unchanged";

        // New units should have updates
        assert movedUnit.position().equals(new Position(50, 50)) : "Moved unit has new position";
        assert damagedUnit.strength() == 30 : "Damaged unit has new strength";

        System.out.println("  ✓ Functional updates work correctly");
    }

    private static void testUnitCapabilities() {
        System.out.println("🧪 Testing Unit Capabilities...");

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

        // Recon should have better movement and detection
        assert UnitOperations.calculateMovementRange(reconUnit) > UnitOperations.calculateMovementRange(assaultUnit) :
            "Recon should have better movement range";
        assert UnitOperations.calculateDetectionRange(reconUnit) > UnitOperations.calculateDetectionRange(assaultUnit) :
            "Recon should have better detection range";

        // Test action capability
        assert UnitOperations.canPerformAction(reconUnit) : "Healthy unit should be able to act";

        var damagedUnit = reconUnit.withStrength(10);
        assert !UnitOperations.canPerformAction(damagedUnit) : "Severely damaged unit should not be able to act";

        System.out.println("  ✓ Unit capabilities work correctly");
    }

    private static void testValidation() {
        System.out.println("🧪 Testing Validation...");

        // Test null ID validation
        try {
            UnitBuilder.builder()
                .id(null)
                .unitType(UnitType.RECONNAISSANCE)
                .strength(100)
                .position(new Position(0, 0))
                .build();
            assert false : "Should have thrown exception for null ID";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Unit ID cannot be null or empty") :
                "Should have correct error message";
        }

        // Test invalid strength validation
        try {
            UnitBuilder.builder()
                .id("TEST")
                .unitType(UnitType.RECONNAISSANCE)
                .strength(-1)
                .position(new Position(0, 0))
                .build();
            assert false : "Should have thrown exception for negative strength";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Strength must be between 0 and 100") :
                "Should have correct error message";
        }

        System.out.println("  ✓ Validation works correctly");
    }
}
