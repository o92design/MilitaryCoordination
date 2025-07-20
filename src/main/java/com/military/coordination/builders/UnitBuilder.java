package com.military.coordination.builders;
import java.util.Set;

import com.military.coordination.model.Position;
import com.military.coordination.model.Unit;
import com.military.coordination.model.UnitStatus;
import com.military.coordination.model.UnitType;

/**
 * Builder for creating Unit instances with fluent API.
 * Provides flexible construction for complex scenarios and testing.
 * Separated from the Unit data structure following functional programming principles.
 */
public final class UnitBuilder {
    private String id;
    private UnitType unitType;
    private int strength;
    private Position position;
    private UnitStatus status;
    private Set<String> equipment;

    // Private constructor - use static factory method
    private UnitBuilder() {}

    /**
     * Creates a new UnitBuilder instance.
     */
    public static UnitBuilder builder() {
        return new UnitBuilder();
    }

    public UnitBuilder id(String id) {
        this.id = id;
        return this;
    }

    public UnitBuilder unitType(UnitType unitType) {
        this.unitType = unitType;
        return this;
    }

    public UnitBuilder strength(int strength) {
        this.strength = strength;
        return this;
    }

    public UnitBuilder position(Position position) {
        this.position = position;
        return this;
    }

    public UnitBuilder status(UnitStatus status) {
        this.status = status;
        return this;
    }

    public UnitBuilder equipment(Set<String> equipment) {
        this.equipment = equipment;
        return this;
    }

    /**
     * Creates a Unit instance from the current builder state.
     * @return new Unit instance
     * @throws IllegalArgumentException if required fields are missing or invalid
     */
    public Unit build() {
        return new Unit(id, unitType, strength, position, status, equipment);
    }

    /**
     * Creates a builder pre-populated with values from an existing unit.
     * Useful for creating modified copies.
     */
    public static UnitBuilder from(Unit unit) {
        return new UnitBuilder()
            .id(unit.id())
            .unitType(unit.unitType())
            .strength(unit.strength())
            .position(unit.position())
            .status(unit.status())
            .equipment(unit.equipment());
    }
}
