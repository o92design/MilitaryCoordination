package com.military.coordination.util;

import java.util.Set;

import com.military.coordination.model.Unit;
import com.military.coordination.model.UnitStatus;
import com.military.coordination.model.UnitType;

/**
 * Pure functional utilities for working with Unit data structures.
 * This class contains no state and only pure functions that operate on Unit objects.
 * Following functional programming principles, all methods are static and side-effect free.
 */
public final class UnitOperations {

    // Private constructor to prevent instantiation - this is a utility class
    private UnitOperations() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Calculates the effectiveness of a unit based on its attributes.
     * Pure function - same input always produces same output.
     *
     * @param unit the unit to evaluate
     * @return the calculated effectiveness value
     */
    public static double calculateEffectiveness(Unit unit) {
        double baseEffectiveness = unit.strength() * unit.unitType().getEffectivenessMultiplier();
        double equipmentBonus = unit.equipment().size() * 5.0; // 5% bonus per equipment item
        return baseEffectiveness + equipmentBonus;
    }

    /**
     * Determines if a unit can perform actions based on its current state.
     * Pure function with clear business logic.
     *
     * @param unit the unit to check
     * @return true if the unit can perform actions, false otherwise
     */
    public static boolean canPerformAction(Unit unit) {
        return unit.status().canAcceptCommands() && unit.strength() >= 20; // Minimum 20% strength to act
    }

    /**
     * Calculates the movement range for a unit based on type and current condition.
     * Pure function that considers unit type and damage state.
     *
     * @param unit the unit to calculate range for
     * @return the movement range in appropriate units
     */
    public static int calculateMovementRange(Unit unit) {
        int baseRange = unit.unitType().getBaseMovementRange();
        double strengthMultiplier = unit.strength() / 100.0; // Reduced by damage
        return (int) (baseRange * strengthMultiplier);
    }

    /**
     * Calculates the detection range for a unit considering equipment bonuses.
     * Pure function with equipment-based modifiers.
     *
     * @param unit the unit to calculate detection range for
     * @return the detection range including equipment bonuses
     */
    public static int calculateDetectionRange(Unit unit) {
        int baseRange = unit.unitType().getBaseDetectionRange();
        double strengthMultiplier = unit.strength() / 100.0;

        // Equipment modifiers
        double equipmentMultiplier = 1.0;
        Set<String> equipment = unit.equipment();

        if (equipment.contains("GPS")) {
            equipmentMultiplier *= 1.2;
        }
        if (equipment.contains("Night Vision")) {
            equipmentMultiplier *= 1.3;
        }

        return (int) (baseRange * strengthMultiplier * equipmentMultiplier);
    }

    /**
     * Calculates the supply capacity for a unit including equipment bonuses.
     * Pure function for logistics calculations.
     *
     * @param unit the unit to calculate supply capacity for
     * @return the total supply capacity
     */
    public static int calculateSupplyCapacity(Unit unit) {
        int baseCapacity = unit.unitType().getBaseSupplyCapacity();
        return unit.equipment().contains("Supply Pack") ? baseCapacity + 100 : baseCapacity;
    }

    /**
     * Determines if a unit is combat-ready based on multiple factors.
     * Composite pure function combining multiple checks.
     *
     * @param unit the unit to evaluate
     * @return true if the unit is combat-ready, false otherwise
     */
    public static boolean isCombatReady(Unit unit) {
        return canPerformAction(unit) &&
               unit.strength() >= 50 &&
               unit.status() != UnitStatus.RESUPPLYING &&
               unit.status() != UnitStatus.DAMAGED;
    }

    /**
     * Calculates the distance a unit can effectively engage targets.
     * Pure function for tactical planning.
     *
     * @param unit the unit to calculate engagement range for
     * @return the effective engagement range
     */
    public static int calculateEngagementRange(Unit unit) {
        int detectionRange = calculateDetectionRange(unit);

        // Different unit types have different engagement capabilities
        return switch (unit.unitType()) {
            case RECONNAISSANCE -> detectionRange; // Can engage at detection range
            case ASSAULT -> (int) (detectionRange * 0.8); // Slightly less than detection
            case SUPPORT -> (int) (detectionRange * 0.6); // Limited engagement capability
            case COMMAND -> (int) (detectionRange * 0.7); // Command units have moderate capability
        };
    }

    /**
     * Estimates the time required for a unit to move to a specific range.
     * Pure function for movement planning.
     *
     * @param unit the unit to calculate movement time for
     * @param distance the distance to travel
     * @return estimated movement time in appropriate time units
     */
    public static double estimateMovementTime(Unit unit, double distance) {
        int movementRange = calculateMovementRange(unit);
        if (movementRange == 0) {
            return Double.POSITIVE_INFINITY; // Cannot move
        }

        // Simple linear calculation - could be enhanced with terrain factors
        return distance / movementRange;
    }

    /**
     * Determines the optimal equipment loadout for a unit type.
     * Pure function that returns recommended equipment based on unit role.
     *
     * @param unitType the type of unit
     * @return a set of recommended equipment
     */
    public static Set<String> getRecommendedEquipment(UnitType unitType) {
        return switch (unitType) {
            case RECONNAISSANCE -> Set.of("Radio", "GPS", "Night Vision", "Binoculars");
            case ASSAULT -> Set.of("Radio", "Night Vision", "Body Armor", "Medical Kit");
            case SUPPORT -> Set.of("Radio", "Supply Pack", "Repair Kit", "Medical Kit");
            case COMMAND -> Set.of("Radio", "GPS", "Communications Array", "Tactical Display");
        };
    }
}
