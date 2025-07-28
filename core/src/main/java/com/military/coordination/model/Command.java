package com.military.coordination.model;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a military command with associated metadata such as type, target,
 * priority, timeout, creation time, and status.
 * <p>
 * This record is immutable and provides validation for its fields. It also
 * offers a convenient constructor for creating new commands
 * with default values for {@code createdAt} and {@code status}, as well as a
 * method for creating a copy with an updated status.
 * </p>
 *
 * @param id        Unique identifier for the command. Must not be {@code null}.
 * @param type      Type of command (e.g., RECONNAISSANCE, MOVE). Must not be
 *                  {@code null}.
 * @param target    Target area or unit for the command. Must not be
 *                  {@code null} or blank.
 * @param priority  Priority level of the command. Must not be {@code null}.
 * @param timeout   Timeout duration for the command execution. Must be a
 *                  positive duration.
 * @param createdAt Timestamp indicating when the command was created. If
 *                  {@code null}, defaults to {@link Instant#now()}.
 * @param status    Current status of the command. If {@code null}, defaults to
 *                  {@link CommandStatus#PENDING}.
 *
 * @throws IllegalArgumentException if any required parameter is {@code null} or
 *                                  invalid.
 */
public record Command(
        UUID id, // Unique identifier for the command
        CommandType type, // Type of command (e.g., RECONNAISSANCE, MOVE)
        String target, // Target area or unit for the command
        Priority priority, // Priority level of the command
        Duration timeout, // Timeout duration for the command execution
        Instant createdAt, // When the command was created
        CommandStatus status // Current status of the command
) {

    /**
     * Constructor for creating new commands (most common use case).
     *
     * @param id       Unique identifier for the command.
     * @param type     Type of command.
     * @param target   Target area or unit for the command.
     * @param priority Priority level of the command.
     * @param timeout  Timeout duration for the command execution.
     */
    @SuppressWarnings("hiding")
    public Command(final UUID id, final CommandType type, final String target, final Priority priority,
            final Duration timeout) {
        this(id, type, target, priority, timeout, Instant.now(), CommandStatus.PENDING);
    }

    /**
     * Compact constructor for validation.
     * Validates all fields and sets default values for createdAt and status if
     * null.
     *
     * @throws IllegalArgumentException if any required parameter is {@code null} or
     *                                  invalid.
     */
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
     * Create a new Command with updated status (functional programming approach).
     * This is a pure data transformation - no business logic
     */
    public Command withStatus(final CommandStatus newStatus) {
        return new Command(id, type, target, priority, timeout, createdAt, newStatus);
    }
}
