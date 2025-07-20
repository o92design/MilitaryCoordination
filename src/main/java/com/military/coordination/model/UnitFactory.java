package com.military.coordination.model;

import java.util.Set;

/**
 * Functional factory for creating Unit instances with type-specific methods.
 * Provides pure functions for unit creation with appropriate defaults for each type.
 * Follows functional programming principles - all methods are pure functions.
 */
public final class UnitFactory {

    // Private constructor to prevent instantiation
    private UnitFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ========== RECONNAISSANCE UNITS ==========

    /**
     * Creates a reconnaissance unit with default settings.
     */
    public static Unit createReconnaissanceUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.RECONNAISSANCE,
            UnitType.RECONNAISSANCE.getDefaultStrength(),
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.RECONNAISSANCE.getStandardEquipment())
        );
    }

    /**
     * Creates a reconnaissance unit with custom strength.
     */
    public static Unit createReconnaissanceUnit(String id, Position position, int strength) {
        return new Unit(
            id,
            UnitType.RECONNAISSANCE,
            strength,
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.RECONNAISSANCE.getStandardEquipment())
        );
    }

    /**
     * Creates a damaged reconnaissance unit.
     */
    public static Unit createDamagedReconnaissanceUnit(String id, Position position, int damagePercentage) {
        int remainingStrength = Math.max(0,
            UnitType.RECONNAISSANCE.getDefaultStrength() - damagePercentage);

        return new Unit(
            id,
            UnitType.RECONNAISSANCE,
            remainingStrength,
            position,
            remainingStrength > 0 ? UnitStatus.DAMAGED : UnitStatus.DISABLED,
            Set.copyOf(UnitType.RECONNAISSANCE.getStandardEquipment())
        );
    }

    /**
     * Creates an elite reconnaissance unit with maximum strength.
     */
    public static Unit createEliteReconnaissanceUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.RECONNAISSANCE,
            100, // Maximum strength
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.RECONNAISSANCE.getStandardEquipment())
        );
    }

    // ========== ASSAULT UNITS ==========

    /**
     * Creates an assault unit with default settings.
     */
    public static Unit createAssaultUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.ASSAULT,
            UnitType.ASSAULT.getDefaultStrength(),
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.ASSAULT.getStandardEquipment())
        );
    }

    /**
     * Creates an assault unit with custom strength.
     */
    public static Unit createAssaultUnit(String id, Position position, int strength) {
        return new Unit(
            id,
            UnitType.ASSAULT,
            strength,
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.ASSAULT.getStandardEquipment())
        );
    }

    /**
     * Creates a damaged assault unit.
     */
    public static Unit createDamagedAssaultUnit(String id, Position position, int damagePercentage) {
        int remainingStrength = Math.max(0,
            UnitType.ASSAULT.getDefaultStrength() - damagePercentage);

        return new Unit(
            id,
            UnitType.ASSAULT,
            remainingStrength,
            position,
            remainingStrength > 0 ? UnitStatus.DAMAGED : UnitStatus.DISABLED,
            Set.copyOf(UnitType.ASSAULT.getStandardEquipment())
        );
    }

    /**
     * Creates an elite assault unit with maximum strength.
     */
    public static Unit createEliteAssaultUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.ASSAULT,
            100, // Maximum strength
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.ASSAULT.getStandardEquipment())
        );
    }

    // ========== SUPPORT UNITS ==========

    /**
     * Creates a support unit with default settings.
     */
    public static Unit createSupportUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.SUPPORT,
            UnitType.SUPPORT.getDefaultStrength(),
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.SUPPORT.getStandardEquipment())
        );
    }

    /**
     * Creates a support unit with custom strength.
     */
    public static Unit createSupportUnit(String id, Position position, int strength) {
        return new Unit(
            id,
            UnitType.SUPPORT,
            strength,
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.SUPPORT.getStandardEquipment())
        );
    }

    /**
     * Creates a damaged support unit.
     */
    public static Unit createDamagedSupportUnit(String id, Position position, int damagePercentage) {
        int remainingStrength = Math.max(0,
            UnitType.SUPPORT.getDefaultStrength() - damagePercentage);

        return new Unit(
            id,
            UnitType.SUPPORT,
            remainingStrength,
            position,
            remainingStrength > 0 ? UnitStatus.DAMAGED : UnitStatus.DISABLED,
            Set.copyOf(UnitType.SUPPORT.getStandardEquipment())
        );
    }

    /**
     * Creates an elite support unit with maximum strength.
     */
    public static Unit createEliteSupportUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.SUPPORT,
            100, // Maximum strength
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.SUPPORT.getStandardEquipment())
        );
    }

    // ========== COMMAND UNITS ==========

    /**
     * Creates a command unit with default settings.
     */
    public static Unit createCommandUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.COMMAND,
            UnitType.COMMAND.getDefaultStrength(),
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.COMMAND.getStandardEquipment())
        );
    }

    /**
     * Creates a command unit with custom strength.
     */
    public static Unit createCommandUnit(String id, Position position, int strength) {
        return new Unit(
            id,
            UnitType.COMMAND,
            strength,
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.COMMAND.getStandardEquipment())
        );
    }

    /**
     * Creates a damaged command unit.
     */
    public static Unit createDamagedCommandUnit(String id, Position position, int damagePercentage) {
        int remainingStrength = Math.max(0,
            UnitType.COMMAND.getDefaultStrength() - damagePercentage);

        return new Unit(
            id,
            UnitType.COMMAND,
            remainingStrength,
            position,
            remainingStrength > 0 ? UnitStatus.DAMAGED : UnitStatus.DISABLED,
            Set.copyOf(UnitType.COMMAND.getStandardEquipment())
        );
    }

    /**
     * Creates an elite command unit with maximum strength.
     */
    public static Unit createEliteCommandUnit(String id, Position position) {
        return new Unit(
            id,
            UnitType.COMMAND,
            100, // Maximum strength
            position,
            UnitStatus.READY,
            Set.copyOf(UnitType.COMMAND.getStandardEquipment())
        );
    }
}
