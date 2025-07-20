package com.military.coordination.core;

import com.military.coordination.model.Position;
import com.military.coordination.model.Unit;
import com.military.coordination.model.UnitFactory;
import com.military.coordination.util.UnitOperations;

public class Main {
    public static void main(String[] args) {
        System.out.println("Military Coordination System Initialized.");

        // Demonstrate new Unit data structure
        System.out.println("\n=== New Unit Data Structure (Issue #14) ===");

        // Create units using type-specific factory methods
        Unit reconUnit = UnitFactory.createReconnaissanceUnit("RECON-ALPHA", new Position(10, 20));
        Unit assaultUnit = UnitFactory.createAssaultUnit("ASSAULT-BETA", new Position(50, 30));
        Unit supportUnit = UnitFactory.createSupportUnit("SUPPORT-GAMMA", new Position(0, 0));
        Unit eliteRecon = UnitFactory.createEliteReconnaissanceUnit("ELITE-DELTA", new Position(100, 200));
        Unit damagedAssault = UnitFactory.createDamagedAssaultUnit("DAMAGED-ECHO", new Position(75, 25), 40);

        System.out.println("Recon Unit: " + reconUnit);
        System.out.println("  - Effectiveness: " + UnitOperations.calculateEffectiveness(reconUnit));
        System.out.println("  - Movement Range: " + UnitOperations.calculateMovementRange(reconUnit));
        System.out.println("  - Detection Range: " + UnitOperations.calculateDetectionRange(reconUnit));
        System.out.println("  - Can Act: " + UnitOperations.canPerformAction(reconUnit));

        System.out.println("\nAssault Unit: " + assaultUnit);
        System.out.println("  - Effectiveness: " + UnitOperations.calculateEffectiveness(assaultUnit));
        System.out.println("  - Movement Range: " + UnitOperations.calculateMovementRange(assaultUnit));
        System.out.println("  - Detection Range: " + UnitOperations.calculateDetectionRange(assaultUnit));

        System.out.println("\nSupport Unit: " + supportUnit);
        System.out.println("  - Supply Capacity: " + UnitOperations.calculateSupplyCapacity(supportUnit));
        System.out.println("  - Movement Range: " + UnitOperations.calculateMovementRange(supportUnit));

        // Demonstrate functional updates
        System.out.println("\n=== Functional Updates ===");
        Unit movedRecon = reconUnit.withPosition(new Position(100, 150));
        Unit damagedAssaultUpdated = assaultUnit.withStrength(45);

        System.out.println("Original Recon Position: " + reconUnit.position());
        System.out.println("Moved Recon Position: " + movedRecon.position());
        System.out.println("Original Assault Strength: " + assaultUnit.strength());
        System.out.println("Damaged Assault Strength: " + damagedAssaultUpdated.strength());
        System.out.println("Damaged Assault Can Act: " + UnitOperations.canPerformAction(damagedAssaultUpdated));

        // Legacy classes removed - replaced with new Unit data structure
        System.out.println("\n=== Legacy Code Replaced ===");
        System.out.println("Legacy units replaced with new Unit data structure.");
        System.out.println("Use UnitFactory to create units with proper type safety.");
    }
}
