package com.military.coordination.demo;

import java.util.Collections;
import java.util.List;

import com.military.coordination.builders.UnitBuilder;
import com.military.coordination.model.Position;
import com.military.coordination.model.SignalTower;
import com.military.coordination.model.Unit;
import com.military.coordination.model.UnitStatus;
import com.military.coordination.model.UnitType;
import com.military.coordination.util.SignalTowerOperations;

/**
 * Demonstration of enhanced SignalTower with position and circular transmission range.
 * Shows how signal towers can coordinate with units within their coverage area.
 */
public class SignalTowerDemo {

    public static void main(String[] args) {
        System.out.println("=== Signal Tower Coordination Demo ===\n");

        // Create a signal tower at a strategic position
        var commandTower = new SignalTower(
            "TOWER-ALPHA",
            new Position(100, 100),
            150,                    // Signal strength
            50.0,                   // Transmission range (50 units radius)
            Collections.emptyList(),
            Collections.emptyList()
        );

        System.out.printf("Command Tower: %s at %s with %d signal strength and %.1f range\n",
            commandTower.id(),
            formatPosition(commandTower.position()),
            commandTower.signalStrength(),
            commandTower.transmissionRange()
        );

        // Create units at various positions
        var recon1 = UnitBuilder.builder()
            .id("RECON-001")
            .unitType(UnitType.RECONNAISSANCE)
            .strength(75)
            .position(new Position(120, 110))  // Within range
            .status(UnitStatus.RECONNOITERING)
            .build();

        var assault1 = UnitBuilder.builder()
            .id("ASSAULT-001")
            .unitType(UnitType.ASSAULT)
            .strength(90)
            .position(new Position(80, 90))    // Within range
            .status(UnitStatus.READY)
            .build();

        var support1 = UnitBuilder.builder()
            .id("SUPPORT-001")
            .unitType(UnitType.SUPPORT)
            .strength(85)
            .position(new Position(200, 200))  // Outside range
            .status(UnitStatus.RESUPPLYING)
            .build();

        var recon2 = UnitBuilder.builder()
            .id("RECON-002")
            .unitType(UnitType.RECONNAISSANCE)
            .strength(70)
            .position(new Position(130, 140))  // Edge of range
            .status(UnitStatus.MOVING)
            .build();

        List<Unit> allUnits = List.of(recon1, assault1, support1, recon2);

        System.out.println("\n=== Unit Status and Range Analysis ===");

        // Check which units are in transmission range
        for (Unit unit : allUnits) {
            boolean inRange = SignalTowerOperations.isUnitInRange(commandTower, unit);
            double distance = calculateDistance(commandTower.position(), unit.position());
            double signalStrength = SignalTowerOperations.calculateEffectiveSignalStrength(commandTower, unit.position());

            System.out.printf("Unit %s at %s: %.1f units away, %s range, %.2f signal strength\n",
                unit.id(),
                formatPosition(unit.position()),
                distance,
                inRange ? "IN" : "OUT OF",
                signalStrength
            );
        }

        // Filter units within range
        List<Unit> unitsInRange = SignalTowerOperations.getUnitsInRange(commandTower, allUnits);

        System.out.printf("\n=== Units in Communication Range (%d total) ===\n", unitsInRange.size());
        for (Unit unit : unitsInRange) {
            System.out.printf("- %s (%s) - Status: %s\n",
                unit.id(),
                unit.unitType(),
                unit.status()
            );
        }

        // Demonstrate tower placement optimization
        var targetPositions = List.of(
            new Position(50, 50),
            new Position(150, 50),
            new Position(150, 150),
            new Position(50, 150)
        );

        Position optimalPosition = SignalTowerOperations.findOptimalTowerPosition(targetPositions);

        System.out.printf("\n=== Optimal Tower Placement ===\n");
        System.out.printf("For positions: %s\n",
            targetPositions.stream()
                .map(SignalTowerDemo::formatPosition)
                .reduce((a, b) -> a + ", " + b)
                .orElse("none")
        );
        System.out.printf("Optimal tower position: %s\n", formatPosition(optimalPosition));

        // Demonstrate tower range overlap
        var secondTower = new SignalTower(
            "TOWER-BRAVO",
            new Position(180, 100),
            120,
            40.0,
            Collections.emptyList(),
            Collections.emptyList()
        );

        boolean hasOverlap = SignalTowerOperations.hasOverlappingRange(commandTower, secondTower);
        System.out.printf("\n=== Tower Network Coverage ===\n");
        System.out.printf("Tower Alpha: %s (range: %.1f)\n",
            formatPosition(commandTower.position()), commandTower.transmissionRange());
        System.out.printf("Tower Bravo: %s (range: %.1f)\n",
            formatPosition(secondTower.position()), secondTower.transmissionRange());
        System.out.printf("Coverage overlap: %s\n", hasOverlap ? "YES" : "NO");

        // Demonstrate functional updates
        System.out.printf("\n=== Functional Updates ===\n");
        var upgradedTower = commandTower
            .withTransmissionRange(75.0)
            .withSignalStrength(200);

        System.out.printf("Original tower range: %.1f, strength: %d\n",
            commandTower.transmissionRange(), commandTower.signalStrength());
        System.out.printf("Upgraded tower range: %.1f, strength: %d\n",
            upgradedTower.transmissionRange(), upgradedTower.signalStrength());

        System.out.println("\n=== Demo Complete ===");
    }

    private static String formatPosition(Position pos) {
        return String.format("(%d,%d)", pos.x(), pos.y());
    }

    private static double calculateDistance(Position from, Position to) {
        int dx = to.x() - from.x();
        int dy = to.y() - from.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
