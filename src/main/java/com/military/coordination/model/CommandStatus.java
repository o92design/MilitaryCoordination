package com.military.coordination.model;

/**
 * Status tracking for command execution lifecycle.
 * Used to track commands through the Tower Trust System processing.
 */
public enum CommandStatus {
    PENDING,      // Command is queued, waiting for processing
    EXECUTING,    // Command is being processed by Unit Lead
    COMPLETED,    // Command was successfully executed
    FAILED,       // Command execution failed
    TIMEOUT,      // Command expired before execution
    CANCELLED     // Command was manually cancelled
}
