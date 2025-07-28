
package com.military.coordination.model;

import java.util.UUID;

import com.military.coordination.component.SignalStrengthComponent;
import com.military.coordination.component.StressComponent;
import com.military.coordination.component.TrustComponent;

/**
 * Immutable record representing a unit leader in the ECS model.
 *
 * @param id                      unique identifier
 * @param name                    leader name
 * @param preferredTower          preferred tower type
 * @param trustComponent          trust component
 * @param signalStrengthComponent signal status component
 * @param stressComponent         stress component
 */
public record Leader(
        UUID id,
        String name,
        TowerType preferredTower,
        TrustComponent trustComponent,
        SignalStrengthComponent signalStrengthComponent,
        StressComponent stressComponent) {
    /**
     * Returns a new Leader with the given signalStrengthComponent.
     *
     * @param newsignalStrengthComponent the new signal component
     * @return new Leader instance
     */
    public Leader withsignalStrengthComponent(SignalStrengthComponent newsignalStrengthComponent) {
        return new Leader(id, name, preferredTower, trustComponent, newsignalStrengthComponent, stressComponent);
    }
}
