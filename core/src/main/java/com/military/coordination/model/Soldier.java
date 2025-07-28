package com.military.coordination.model;

import java.util.UUID;

import com.military.coordination.component.NameComponent;
import com.military.coordination.component.SignalStrengthComponent;
import com.military.coordination.component.StressComponent;
import com.military.coordination.component.TrustComponent;

/**
 * Immutable data record representing a soldier in the game.
 *
 * @param id                      Unique identifier for the soldier
 * @param name                    Human-readable name or callsign
 * @param role                    The role of the soldier (e.g., SUPPORT, MEDIC,
 *                                etc.)
 * @param trustComponent          The trust component for this soldier
 * @param signalStrengthComponent The signal component for this soldier
 * @param stressComponent         The stress component for this soldier
 */
public record Soldier(
        UUID id,
        NameComponent name,
        SoldierRole role,
        TrustComponent trustComponent,
        SignalStrengthComponent signalStrengthComponent,
        StressComponent stressComponent) {
    /**
     * Compact constructor for validation.
     *
     * @throws IllegalArgumentException if any required field is invalid
     */
    public Soldier {
        if (id == null) {
            throw new IllegalArgumentException("Soldier ID cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Soldier name cannot be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("Soldier role cannot be null");
        }
        if (trustComponent == null) {
            throw new IllegalArgumentException("TrustComponent cannot be null");
        }
        if (signalStrengthComponent == null) {
            throw new IllegalArgumentException("signalStregthComponent cannot be null");
        }
    }
}
