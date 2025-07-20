package com.military.coordination.demo;

import com.military.coordination.model.Position;
import com.military.coordination.util.PositionOperations;

/**
 * Demo showing the functional programming approach for Position operations.
 * Demonstrates pure functions and immutable data structures.
 */
public class PositionDemo {

    public static void main(String[] args) {
        System.out.println("=== Position Functional Programming Demo ===");

        // Create immutable position objects
        var headquarter = new Position(0, 0);
        var unit1 = new Position(30, 40);
        var unit2 = new Position(60, 80);

        System.out.println("Headquarter: " + headquarter);
        System.out.println("Unit 1: " + unit1);
        System.out.println("Unit 2: " + unit2);

        // Use pure functions for calculations
        double distance1 = PositionOperations.calculateDistance(headquarter, unit1);
        double distance2 = PositionOperations.calculateDistance(headquarter, unit2);
        int manhattanDist = PositionOperations.calculateManhattanDistance(unit1, unit2);

        System.out.println("\n=== Distance Calculations ===");
        System.out.println("HQ to Unit 1: " + distance1);
        System.out.println("HQ to Unit 2: " + distance2);
        System.out.println("Unit 1 to Unit 2 (Manhattan): " + manhattanDist);

        // Check communication range
        double commRange = 100.0;
        boolean unit1InRange = PositionOperations.isWithinRange(headquarter, unit1, commRange);
        boolean unit2InRange = PositionOperations.isWithinRange(headquarter, unit2, commRange);

        System.out.println("\n=== Communication Range Check (100km) ===");
        System.out.println("Unit 1 in range: " + unit1InRange);
        System.out.println("Unit 2 in range: " + unit2InRange);

        // Create patrol positions using offset
        var patrol1 = PositionOperations.createOffset(unit1, 10, 0);
        var patrol2 = PositionOperations.createOffset(unit1, 0, 10);
        var patrol3 = PositionOperations.createOffset(unit1, -10, 0);

        System.out.println("\n=== Patrol Positions (Offset from Unit 1) ===");
        System.out.println("Original Unit 1: " + unit1);
        System.out.println("Patrol East: " + patrol1);
        System.out.println("Patrol North: " + patrol2);
        System.out.println("Patrol West: " + patrol3);

        // Demonstrate immutability - original positions unchanged
        System.out.println("\n=== Immutability Verification ===");
        System.out.println("Unit 1 after operations: " + unit1 + " (unchanged)");
        System.out.println("Headquarter after operations: " + headquarter + " (unchanged)");
    }
}
