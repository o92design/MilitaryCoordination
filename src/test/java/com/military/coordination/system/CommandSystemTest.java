package com.military.coordination.system;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.military.coordination.model.Command;
import com.military.coordination.model.CommandType;
import com.military.coordination.model.Priority;

@DisplayName("CommandSystem - Functional Command Operations")
class CommandSystemTest {

    @Nested
    @DisplayName("Timeout Management")
    class TimeoutManagement {

        @Test
        @DisplayName("Should detect when command has not timed out")
        void shouldDetectWhenCommandHasNotTimedOut() {
            var command = new Command("CMD-001", CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));
            var currentTime = command.createdAt().plusSeconds(30); // 30 seconds after creation

            assertThat(CommandSystem.hasTimedOut(command, currentTime)).isFalse();
        }

        @Test
        @DisplayName("Should detect when command has timed out")
        void shouldDetectWhenCommandHasTimedOut() {
            var command = new Command("CMD-001", CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(5));
            var currentTime = command.createdAt().plus(Duration.ofMinutes(10)); // 10 minutes after creation

            assertThat(CommandSystem.hasTimedOut(command, currentTime)).isTrue();
        }

        @Test
        @DisplayName("Should handle exact timeout boundary")
        void shouldHandleExactTimeoutBoundary() {
            var command = new Command("CMD-001", CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(5));
            var exactTimeoutTime = command.createdAt().plus(command.timeout());

            assertThat(CommandSystem.hasTimedOut(command, exactTimeoutTime)).isFalse(); // Exactly at timeout = not timed out yet
            assertThat(CommandSystem.hasTimedOut(command, exactTimeoutTime.plusNanos(1))).isTrue(); // 1 nanosecond after = timed out
        }
    }

    @Nested
    @DisplayName("Cost Calculation")
    class CostCalculation {

        @Test
        @DisplayName("Should calculate base costs correctly for each command type")
        void shouldCalculateBaseCostsCorrectlyForEachCommandType() {
            assertThat(CommandSystem.getBaseCost(CommandType.STATUS_REPORT)).isEqualTo(1);
            assertThat(CommandSystem.getBaseCost(CommandType.RECONNAISSANCE)).isEqualTo(2);
            assertThat(CommandSystem.getBaseCost(CommandType.MOVE)).isEqualTo(2);
            assertThat(CommandSystem.getBaseCost(CommandType.EMERGENCY)).isEqualTo(3);
            assertThat(CommandSystem.getBaseCost(CommandType.ESTABLISH_COMMS)).isEqualTo(4);
        }

        @Test
        @DisplayName("Should calculate cost with perfect conditions")
        void shouldCalculateCostWithPerfectConditions() {
            var command = new Command("CMD-001", CommandType.STATUS_REPORT, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));
            // Perfect conditions: max trust, no stress, perfect signal
            int cost = CommandSystem.calculateCost(command, 100, 0, 100);

            assertThat(cost).isEqualTo(1); // Base cost for STATUS_REPORT with no multipliers
        }

        @Test
        @DisplayName("Should calculate cost with worst conditions")
        void shouldCalculateCostWithWorstConditions() {
            var command = new Command("CMD-001", CommandType.EMERGENCY, "GRID-17", Priority.HIGH, Duration.ofMinutes(10));
            // Worst conditions: no trust, max stress, no signal
            int cost = CommandSystem.calculateCost(command, 0, 100, 0);

            // With worst conditions: trustMultiplier=1.0, stressMultiplier=1.0, signalMultiplier=1.0
            // Cost = baseCost * (1 + 1.0 + 1.0 + 1.0) = 3 * 4 = 12
            assertThat(cost).isEqualTo(12);
        }

        @Test
        @DisplayName("Should validate percentage inputs")
        void shouldValidatePercentageInputs() {
            var command = new Command("CMD-001", CommandType.RECONNAISSANCE, "GRID-17", Priority.NORMAL, Duration.ofMinutes(10));

            assertThatThrownBy(() -> CommandSystem.calculateCost(command, -1, 50, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit trust must be between 0 and 100");

            assertThatThrownBy(() -> CommandSystem.calculateCost(command, 50, 101, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit stress must be between 0 and 100");

            assertThatThrownBy(() -> CommandSystem.calculateCost(command, 50, 50, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Signal strength must be between 0 and 100");
        }
    }

    @Nested
    @DisplayName("Priority Management")
    class PriorityManagement {

        @Test
        @DisplayName("Should return correct priority scores")
        void shouldReturnCorrectPriorityScores() {
            var highPriorityCommand = new Command("CMD-001", CommandType.EMERGENCY, "GRID-17", Priority.HIGH, Duration.ofMinutes(5));
            var normalPriorityCommand = new Command("CMD-002", CommandType.RECONNAISSANCE, "GRID-18", Priority.NORMAL, Duration.ofMinutes(10));
            var lowPriorityCommand = new Command("CMD-003", CommandType.STATUS_REPORT, "UNIT-ALPHA", Priority.LOW, Duration.ofMinutes(15));

            assertThat(CommandSystem.getPriorityScore(highPriorityCommand)).isEqualTo(1);
            assertThat(CommandSystem.getPriorityScore(normalPriorityCommand)).isEqualTo(2);
            assertThat(CommandSystem.getPriorityScore(lowPriorityCommand)).isEqualTo(3);
        }

        @Test
        @DisplayName("Should identify urgent commands")
        void shouldIdentifyUrgentCommands() {
            var emergencyCommand = new Command("CMD-001", CommandType.EMERGENCY, "GRID-17", Priority.NORMAL, Duration.ofMinutes(5));
            var highPriorityCommand = new Command("CMD-002", CommandType.RECONNAISSANCE, "GRID-18", Priority.HIGH, Duration.ofMinutes(10));
            var normalCommand = new Command("CMD-003", CommandType.STATUS_REPORT, "UNIT-ALPHA", Priority.NORMAL, Duration.ofMinutes(15));

            assertThat(CommandSystem.isUrgent(emergencyCommand)).isTrue(); // Emergency type
            assertThat(CommandSystem.isUrgent(highPriorityCommand)).isTrue(); // High priority
            assertThat(CommandSystem.isUrgent(normalCommand)).isFalse(); // Normal command
        }
    }
}
