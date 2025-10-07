package com.vwdigitalhub.robots.application.domain.service;

import com.vwdigitalhub.robots.application.domain.exception.InvalidWorkspaceException;
import com.vwdigitalhub.robots.application.domain.exception.OutOfBoundsException;
import com.vwdigitalhub.robots.application.domain.exception.PositionOccupiedException;
import com.vwdigitalhub.robots.application.domain.model.Direction;
import com.vwdigitalhub.robots.application.domain.model.Position;
import com.vwdigitalhub.robots.application.domain.model.Workspace;
import com.vwdigitalhub.robots.application.domain.model.instructions.RobotInstruction;
import com.vwdigitalhub.robots.application.domain.port.in.ExecuteInstructionsUseCase;
import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ControlRobotCommand;
import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ControlRobotCommandResponse;
import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ExecuteInstructionsService;
import com.vwdigitalhub.robots.application.domain.service.util.InstructionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteInstructionsServiceTest {

    private Workspace ws;
    private ExecuteInstructionsUseCase useCase;

    @BeforeEach
    void setUp() {
        // GIVEN
        ws = new Workspace(5, 5);
        useCase = new ExecuteInstructionsService();
    }

    @Test
    void shouldMatchSampleCaseFromStatement() {
        // WHEN
        ControlRobotCommandResponse r1 = useCase.execute(
                new ControlRobotCommand(ws, new Position(1, 2), Direction.N, InstructionParser.from("LMLMLMLMM")));
        ControlRobotCommandResponse r2 = useCase.execute(
                new ControlRobotCommand(ws, new Position(3, 3), Direction.E, InstructionParser.from("MMRMMRMRRM")));
        // THEN
        assertEquals("1 3 N", r1.toString());
        assertEquals("5 1 E", r2.toString());
    }

    @Test
    void shouldThrowWhenStartingOutsideWorkspace() {
        // GIVEN
        ControlRobotCommand cmd =
                new ControlRobotCommand(ws, new Position(6, 0), Direction.N, InstructionParser.from(""));
        // WHEN + THEN
        assertThrows(OutOfBoundsException.class, () -> useCase.execute(cmd));
    }

    @Test
    void shouldThrowWhenMovingOutsideWorkspace() {
        // GIVEN
        ControlRobotCommand cmd =
                new ControlRobotCommand(ws, new Position(5, 5), Direction.N, InstructionParser.from("M"));
        // WHEN + THEN
        assertThrows(OutOfBoundsException.class, () -> useCase.execute(cmd));
    }

    @Test
    void shouldThrowWhenFinalPositionCollides() {
        // GIVEN
        var instr = InstructionParser.from("M");
        ControlRobotCommand first = new ControlRobotCommand(ws, new Position(0, 0), Direction.N, instr);
        ControlRobotCommandResponse r1 = useCase.execute(first);
        assertEquals("0 1 N", r1.toString());
        // WHEN
        ControlRobotCommand second = new ControlRobotCommand(ws, new Position(0, 0), Direction.N, instr);
        // THEN
        assertThrows(PositionOccupiedException.class, () -> useCase.execute(second));
    }

    @Test
    void shouldThrowWhenInvalidWorkspace() {
        // GIVEN + WHEN + THEN
        assertThrows(InvalidWorkspaceException.class, () -> new Workspace(-1, 5));
        assertThrows(InvalidWorkspaceException.class, () -> new Workspace(5, -1));
    }

    @Test
    void shouldKeepInitialStateWithEmptyInstructions() {
        // WHEN
        ControlRobotCommandResponse res = useCase.execute(
                new ControlRobotCommand(ws, new Position(0, 0), Direction.E, InstructionParser.from("   ")));
        // THEN
        assertEquals("0 0 E", res.toString());
    }

    @Test
    void shouldParseInstructionsCaseInsensitively() {
        // GIVEN
        List<RobotInstruction> lower = InstructionParser.from("lmr");
        List<RobotInstruction> mixed = InstructionParser.from("LmR");
        List<RobotInstruction> upper = InstructionParser.from("LMR");
        // WHEN + THEN
        assertEquals(upper.size(), lower.size());
        assertEquals(upper.size(), mixed.size());
        for (int i = 0; i < upper.size(); i++) {
            assertEquals(upper.get(i).getClass(), lower.get(i).getClass());
            assertEquals(upper.get(i).getClass(), mixed.get(i).getClass());
        }
    }

    @Test
    void shouldReturnSameDirectionAfterFullRotation() {
        // WHEN
        ControlRobotCommandResponse lefts = useCase.execute(
                new ControlRobotCommand(ws, new Position(2, 2), Direction.N, InstructionParser.from("LLLL"))); // -> 2 2 N
        ControlRobotCommandResponse rights = useCase.execute(
                new ControlRobotCommand(ws, new Position(0, 0), Direction.N, InstructionParser.from("RRRR"))); // -> 0 0 N
        // THEN
        assertEquals("2 2 N", lefts.toString());
        assertEquals("0 0 N", rights.toString());
    }

    @ParameterizedTest
    @EnumSource(value = Direction.class, names = {"S", "W"})
    void shouldThrowWhenMovingBelowZero(Direction dir) {
        // GIVEN
        var instr = InstructionParser.from("M");
        // WHEN
        var cmd = new ControlRobotCommand(ws, new Position(0, 0), dir, instr);
        // THEN
        assertThrows(OutOfBoundsException.class, () -> useCase.execute(cmd));
    }

    @Test
    void shouldThrowOnInvalidInstructionChar() {
        // GIVEN + WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> InstructionParser.from("MXM"));
    }

    @Test
    void shouldTrimInstructionsInput() {
        // WHEN
        ControlRobotCommandResponse res = useCase.execute(
                new ControlRobotCommand(ws, new Position(1, 2), Direction.N, InstructionParser.from("  LMLMLMLMM   ")));
        // THEN
        assertEquals("1 3 N", res.toString());
    }
}
