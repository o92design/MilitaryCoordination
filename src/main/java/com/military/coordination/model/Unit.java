package com.military.coordination.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Immutable record representing a military unit.
 * Pure data structure following functional programming principles.
 * Use UnitFactory for creating units and UnitOperations for behavior.
 */
public record Unit(
    String id,
    UnitType unitType,
    int strength,
    Position position,
    UnitStatus status,
    Set<String> equipment
) {
    // Compact constructor for validation and normalization
    public Unit {
        // Validate inputs
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty");
        }
        if (unitType == null) {
            throw new IllegalArgumentException("Unit type cannot be null");
        }
        if (strength < 0 || strength > 100) {
            throw new IllegalArgumentException("Strength must be between 0 and 100");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        // Normalize and set defaults
        id = id.trim();
        status = status != null ? status : UnitStatus.READY;
        equipment = equipment != null ?
            Collections.unmodifiableSet(new HashSet<>(equipment)) :
            Set.of();
    }

    // Functional update methods (return new instances)
    public Unit withPosition(Position newPosition) {
        return new Unit(id, unitType, strength, newPosition, status, equipment);
    }

    public Unit withStatus(UnitStatus newStatus) {
        return new Unit(id, unitType, strength, position, newStatus, equipment);
    }

    public Unit withStrength(int newStrength) {
        return new Unit(id, unitType, newStrength, position, status, equipment);
    }

    public Unit withEquipment(Set<String> newEquipment) {
        return new Unit(id, unitType, strength, position, status, newEquipment);
    }

    // Override equipment accessor to return defensive copy
    @Override
    public Set<String> equipment() {
        return new HashSet<>(equipment);
    }
}
