package com.military.coordination.model;

import java.util.Set;

/**
 * Represents different types of military units with their specific capabilities.
 * Each unit type has different characteristics for movement, detection, and combat.
 * Stores characteristics directly in the enum following functional programming principles.
 */
public enum UnitType {
    /**
     * Reconnaissance units - fast, high detection range, low combat strength
     */
    RECONNAISSANCE(
        "Reconnaissance Unit",
        150, // High movement range
        200, // High detection range
        0.8, // Good effectiveness multiplier
        0,   // No supply capacity
        80,  // Default strength (lighter units)
        Set.of("Binoculars", "Communication Radio", "Light Weapons")
    ),

    /**
     * Assault units - medium speed, medium detection, high combat strength
     */
    ASSAULT(
        "Assault Unit",
        100, // Medium movement range
        100, // Medium detection range
        1.2, // High effectiveness multiplier
        0,   // No supply capacity
        100, // Default strength (full combat ready)
        Set.of("Heavy Weapons", "Body Armor", "Grenades", "Communication Radio")
    ),

    /**
     * Support units - slow, low detection, high supply capacity
     */
    SUPPORT(
        "Support Unit",
        75,  // Low movement range
        75,  // Low detection range
        0.6, // Lower effectiveness multiplier
        500, // High supply capacity
        90,  // Default strength (well-equipped)
        Set.of("Medical Supplies", "Repair Tools", "Extra Ammunition", "Communication Radio")
    ),

    /**
     * Command units - medium stats, coordination bonuses
     */
    COMMAND(
        "Command Unit",
        100, // Medium movement range
        150, // Good detection range
        1.0, // Standard effectiveness
        100, // Some supply capacity
        85,  // Default strength (officer unit)
        Set.of("Command Radio", "Maps", "Light Weapons", "Tactical Equipment")
    );

    private final String displayName;
    private final int baseMovementRange;
    private final int baseDetectionRange;
    private final double effectivenessMultiplier;
    private final int baseSupplyCapacity;
    private final int defaultStrength;
    private final Set<String> standardEquipment;

    UnitType(String displayName, int baseMovementRange, int baseDetectionRange,
             double effectivenessMultiplier, int baseSupplyCapacity,
             int defaultStrength, Set<String> standardEquipment) {
        this.displayName = displayName;
        this.baseMovementRange = baseMovementRange;
        this.baseDetectionRange = baseDetectionRange;
        this.effectivenessMultiplier = effectivenessMultiplier;
        this.baseSupplyCapacity = baseSupplyCapacity;
        this.defaultStrength = defaultStrength;
        this.standardEquipment = Set.copyOf(standardEquipment);
    }    // Direct access methods
    public String getDisplayName() {
        return displayName;
    }

    public int getBaseMovementRange() {
        return baseMovementRange;
    }

    public int getBaseDetectionRange() {
        return baseDetectionRange;
    }

    public double getEffectivenessMultiplier() {
        return effectivenessMultiplier;
    }

    public int getBaseSupplyCapacity() {
        return baseSupplyCapacity;
    }

    public int getDefaultStrength() {
        return defaultStrength;
    }

    public Set<String> getStandardEquipment() {
        return standardEquipment;
    }
}
