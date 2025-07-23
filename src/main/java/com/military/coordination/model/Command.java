package com.military.coordination.model;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Command data structure representing military orders in the Tower Trust System.
 * Immutable record with functional update methods for state management.
 */
public record Command(
    UUID id,                // Unique identifier for the command
    CommandType type,         // Type of command (e.g., RECONNAISSANCE, MOVE)
    String target,            // Target area or unit for the command
    Priority priority,        // Priority level of the command
    Duration timeout,         // Timeout duration for the command execution
    Instant createdAt,        // When the command was created
    CommandStatus status      // Current status of the command
) {

    // Constructor for creating new commands (most common use case)
    public Command(UUID id, CommandType type, String target, Priority priority, Duration timeout) {
        this(id, type, target, priority, timeout, Instant.now(), CommandStatus.PENDING);
    }

    // Compact constructor for validation
    public Command {
        if (id == null) {
            throw new IllegalArgumentException("Command ID cannot be null");
        }
        if (target == null || target.isBlank()) {
            throw new IllegalArgumentException("Target cannot be null or empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Command type cannot be null");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        if (timeout == null || timeout.isNegative() || timeout.isZero()) {
            throw new IllegalArgumentException("Timeout must be a positive duration");
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (status == null) {
            status = CommandStatus.PENDING;
        }
    }

    /**
     * Create a new Command with updated status (functional programming approach)
     * This is a pure data transformation - no business logic
     */
    public Command withStatus(CommandStatus newStatus) {
        return new Command(id, type, target, priority, timeout, createdAt, newStatus);
    }
}
