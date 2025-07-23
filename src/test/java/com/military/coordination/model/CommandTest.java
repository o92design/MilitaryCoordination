package com.military.coordination.model;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.military.coordination.system.CommandSystem;

@DisplayName("Command Data Structure")
class CommandTest {

    @Nested
    @DisplayName("Command Creation")
    class CommandCreation {

        @Test
        @DisplayName("Should create reconnaissance command")
        void shouldCreateReconnaissanceCommand() {
            var commandId = UUID.randomUUID();
            var command = new Command(
                commandId,
                CommandType.RECONNAISSANCE,
                "GRID-17", // target area
                Priority.NORMAL,
                Duration.ofMinutes(10) // timeout
            );

            assertThat(command.id()).isEqualTo(commandId);
            assertThat(command.type()).isEqualTo(CommandType.RECONNAISSANCE);
            assertThat(command.target()).isEqualTo("GRID-17");
            assertThat(command.priority()).isEqualTo(Priority.NORMAL);
            assertThat(command.timeout()).isEqualTo(Duration.ofMinutes(10));
        }

        @Test
        @DisplayName("Should reject null command ID")
        void shouldRejectNullCommandId() {
            assertThatThrownBy(this::createCommandWithNullId)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Command ID cannot be null");
        }

        @Test
        @DisplayName("Should reject empty target")
        void shouldRejectEmptyTarget() {
            assertThatThrownBy(this::createCommandWithEmptyTarget)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Target cannot be null or empty");
        }

        @Test
        @DisplayName("Should reject null target")
        void shouldRejectNullTarget() {
            assertThatThrownBy(this::createCommandWithNullTarget)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Target cannot be null or empty");
        }

        @Test
        @DisplayName("Should reject blank target")
        void shouldRejectBlankTarget() {
            assertThatThrownBy(this::createCommandWithBlankTarget)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Target cannot be null or empty");
        }

        @Test
        @DisplayName("Should reject null command type")
        void shouldRejectNullCommandType() {
            assertThatThrownBy(this::createCommandWithNullType)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Command type cannot be null");
        }

        @Test
        @DisplayName("Should reject null priority")
        void shouldRejectNullPriority() {
            assertThatThrownBy(this::createCommandWithNullPriority)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Priority cannot be null");
        }

        @Test
        @DisplayName("Should reject null timeout")
        void shouldRejectNullTimeout() {
            assertThatThrownBy(this::createCommandWithNullTimeout)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Timeout must be a positive duration");
        }

        @Test
        @DisplayName("Should reject zero timeout")
        void shouldRejectZeroTimeout() {
            assertThatThrownBy(this::createCommandWithZeroTimeout)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Timeout must be a positive duration");
        }

        @Test
        @DisplayName("Should reject negative timeout")
        void shouldRejectNegativeTimeout() {
            assertThatThrownBy(this::createCommandWithNegativeTimeout)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Timeout must be a positive duration");
        }

        // Helper methods for exception testing (extracted from lambdas)
        private Command createCommandWithNullId() {
            return new Command(null, CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));
        }

        private Command createCommandWithEmptyTarget() {
            return new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "", Priority.NORMAL, Duration.ofMinutes(10));
        }

        private Command createCommandWithNullTarget() {
            return new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, null, Priority.NORMAL, Duration.ofMinutes(10));
        }

        private Command createCommandWithBlankTarget() {
            return new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "   ", Priority.NORMAL, Duration.ofMinutes(10));
        }

        private Command createCommandWithNullType() {
            return new Command(UUID.randomUUID(), null, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));
        }

        private Command createCommandWithNullPriority() {
            return new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", null, Duration.ofMinutes(10));
        }

        private Command createCommandWithNullTimeout() {
            return new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, null);
        }

        private Command createCommandWithZeroTimeout() {
            return new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ZERO);
        }

        private Command createCommandWithNegativeTimeout() {
            return new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(-5));
        }

        @Test
        @DisplayName("Should create command with full constructor")
        void shouldCreateCommandWithFullConstructor() {
            var commandId = UUID.randomUUID();
            var createdAt = Instant.now().minusSeconds(30);
            var command = new Command(
                commandId,
                CommandType.EMERGENCY,
                "GRID-17",
                Priority.HIGH,
                Duration.ofMinutes(5),
                createdAt,
                CommandStatus.EXECUTING
            );

            assertThat(command.id()).isEqualTo(commandId);
            assertThat(command.type()).isEqualTo(CommandType.EMERGENCY);
            assertThat(command.target()).isEqualTo("GRID-17");
            assertThat(command.priority()).isEqualTo(Priority.HIGH);
            assertThat(command.timeout()).isEqualTo(Duration.ofMinutes(5));
            assertThat(command.createdAt()).isEqualTo(createdAt);
            assertThat(command.status()).isEqualTo(CommandStatus.EXECUTING);
        }

        @Test
        @DisplayName("Should set default values for createdAt and status")
        void shouldSetDefaultValuesForCreatedAtAndStatus() {
            var beforeCreation = Instant.now();
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));
            var afterCreation = Instant.now();

            assertThat(command.createdAt()).isBetween(beforeCreation, afterCreation);
            assertThat(command.status()).isEqualTo(CommandStatus.PENDING);
        }

        @Test
        @DisplayName("Should handle all command types")
        void shouldHandleAllCommandTypes() {
            // Test that all CommandType enum values can be used
            for (CommandType type : CommandType.values()) {
                var command = new Command(UUID.randomUUID(), type, "TARGET-" + type, Priority.NORMAL, Duration.ofMinutes(5));
                assertThat(command.type()).isEqualTo(type);
            }
        }

        @Test
        @DisplayName("Should handle all priority levels")
        void shouldHandleAllPriorityLevels() {
            // Test that all Priority enum values can be used
            for (Priority priority : Priority.values()) {
                var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "TARGET", priority, Duration.ofMinutes(5));
                assertThat(command.priority()).isEqualTo(priority);
            }
        }

        @Test
        @DisplayName("Should handle various timeout durations")
        void shouldHandleVariousTimeoutDurations() {
            var command1 = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofSeconds(1));
            var command2 = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(30));
            var command3 = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofHours(2));

            assertThat(command1.timeout()).isEqualTo(Duration.ofSeconds(1));
            assertThat(command2.timeout()).isEqualTo(Duration.ofMinutes(30));
            assertThat(command3.timeout()).isEqualTo(Duration.ofHours(2));
        }

        @Test
        @DisplayName("Should handle null createdAt by setting default")
        void shouldHandleNullCreatedAtBySettingDefault() {
            // Test the compact constructor's null handling for createdAt
            var beforeCreation = Instant.now();
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL,
                                    Duration.ofMinutes(10), null, CommandStatus.PENDING);
            var afterCreation = Instant.now();

            assertThat(command.createdAt()).isNotNull();
            assertThat(command.createdAt()).isBetween(beforeCreation, afterCreation);
        }

        @Test
        @DisplayName("Should handle null status by setting default")
        void shouldHandleNullStatusBySettingDefault() {
            // Test the compact constructor's null handling for status
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL,
                                    Duration.ofMinutes(10), Instant.now(), null);

            assertThat(command.status()).isNotNull();
            assertThat(command.status()).isEqualTo(CommandStatus.PENDING);
        }
    }

    @Nested
    @DisplayName("Command Queue Integration")
    class CommandQueueIntegration {

        @Test
        @DisplayName("Should be comparable by priority")
        void shouldBeComparableByPriority() {
            var highPriority = new Command(UUID.randomUUID(), CommandType.EMERGENCY, "GRID-17", Priority.HIGH, Duration.ofMinutes(5));
            var normalPriority = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-18", Priority.NORMAL, Duration.ofMinutes(10));
            var lowPriority = new Command(UUID.randomUUID(), CommandType.STATUS_REPORT, "UNIT-ALPHA", Priority.LOW, Duration.ofMinutes(15));

            // HIGH < NORMAL < LOW (based on the getValue() method where HIGH=1, NORMAL=2, LOW=3)
            assertThat(highPriority.priority().getValue()).isLessThan(normalPriority.priority().getValue());
            assertThat(normalPriority.priority().getValue()).isLessThan(lowPriority.priority().getValue());
        }

        @Test
        @DisplayName("Should track creation timestamp")
        void shouldTrackCreationTimestamp() {
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));

            assertThat(command.createdAt()).isNotNull();
            assertThat(command.createdAt()).isBeforeOrEqualTo(Instant.now());
        }
    }

    @Nested
    @DisplayName("Command State Management")
    class CommandStateManagement {

        @Test
        @DisplayName("Should create with PENDING status by default")
        void shouldCreateWithPendingStatus() {
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));

            assertThat(command.status()).isEqualTo(CommandStatus.PENDING);
        }

        @Test
        @DisplayName("Should transition to EXECUTING status")
        void shouldTransitionToExecutingStatus() {
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));
            var executing = command.withStatus(CommandStatus.EXECUTING);

            assertThat(executing.status()).isEqualTo(CommandStatus.EXECUTING);
            assertThat(executing.id()).isEqualTo(command.id()); // Same command, different state
            // Verify all other fields remain unchanged
            assertThat(executing.type()).isEqualTo(command.type());
            assertThat(executing.target()).isEqualTo(command.target());
            assertThat(executing.priority()).isEqualTo(command.priority());
            assertThat(executing.timeout()).isEqualTo(command.timeout());
            assertThat(executing.createdAt()).isEqualTo(command.createdAt());
        }

        @Test
        @DisplayName("Should handle all status transitions")
        void shouldHandleAllStatusTransitions() {
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));

            // Test all possible status transitions
            var executing = command.withStatus(CommandStatus.EXECUTING);
            var completed = executing.withStatus(CommandStatus.COMPLETED);
            var failed = command.withStatus(CommandStatus.FAILED);
            var timeout = command.withStatus(CommandStatus.TIMEOUT);
            var cancelled = command.withStatus(CommandStatus.CANCELLED);

            assertThat(executing.status()).isEqualTo(CommandStatus.EXECUTING);
            assertThat(completed.status()).isEqualTo(CommandStatus.COMPLETED);
            assertThat(failed.status()).isEqualTo(CommandStatus.FAILED);
            assertThat(timeout.status()).isEqualTo(CommandStatus.TIMEOUT);
            assertThat(cancelled.status()).isEqualTo(CommandStatus.CANCELLED);
        }

        @Test
        @DisplayName("Should create immutable copies with withStatus")
        void shouldCreateImmutableCopiesWithWithStatus() {
            var original = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));
            var modified = original.withStatus(CommandStatus.EXECUTING);

            // Original should be unchanged
            assertThat(original.status()).isEqualTo(CommandStatus.PENDING);
            assertThat(modified.status()).isEqualTo(CommandStatus.EXECUTING);

            // They should be different objects
            assertThat(original).isNotSameAs(modified);
        }

        @Test
        @DisplayName("Should check if command has timed out")
        void shouldCheckIfCommandHasTimedOut() {
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofSeconds(1));

            // Test immediately after creation - should not be timed out
            assertThat(CommandSystem.hasTimedOut(command)).isFalse();

            // Test with explicit future time - deterministic without Thread.sleep
            var futureTime = command.createdAt().plus(Duration.ofSeconds(2)); // Beyond timeout
            assertThat(CommandSystem.hasTimedOut(command, futureTime)).isTrue();
        }
    }

    @Nested
    @DisplayName("Game Design Integration")
    class GameDesignIntegration {

        @Test
        @DisplayName("Should support all command types from game design")
        void shouldSupportAllCommandTypesFromGameDesign() {
            // Based on your game design: scout, move, establish comms, report status
            assertThat(CommandType.values()).contains(
                CommandType.RECONNAISSANCE,    // "scout Grid-17"
                CommandType.MOVE,             // relocate units
                CommandType.ESTABLISH_COMMS,  // set up signal towers
                CommandType.STATUS_REPORT,    // request intel update
                CommandType.EMERGENCY         // high-priority interruptions
            );
        }

        @Test
        @DisplayName("Should calculate command cost based on game mechanics")
        void shouldCalculateCommandCostBasedOnGameMechanics() {
            // From your ACE system: cost depends on unit trust, stress, signal range
            var command = new Command(UUID.randomUUID(), CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));

            // Mock unit state for cost calculation
            var unitTrust = 85; // High trust = lower cost
            var unitStress = 20; // Low stress = lower cost
            var signalStrength = 90; // Strong signal = lower cost

            int baseCostForRecon = 2; // Base cost for RECONNAISSANCE
            int calculatedCost = CommandSystem.calculateCost(command, unitTrust, unitStress, signalStrength);

            // With high trust, low stress, strong signal, cost should be close to base cost
            assertThat(calculatedCost)
                .isGreaterThanOrEqualTo(1)
                .isLessThanOrEqualTo(baseCostForRecon + 1); // Allow some variance
        }
    }
}
