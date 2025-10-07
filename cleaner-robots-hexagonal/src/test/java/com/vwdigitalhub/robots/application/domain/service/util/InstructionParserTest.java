package com.vwdigitalhub.robots.application.domain.service.util;

import com.vwdigitalhub.robots.application.domain.model.instructions.MoveForward;
import com.vwdigitalhub.robots.application.domain.model.instructions.RobotInstruction;
import com.vwdigitalhub.robots.application.domain.model.instructions.TurnLeft;
import com.vwdigitalhub.robots.application.domain.model.instructions.TurnRight;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstructionParserTest {

    @Test
    void shouldReturnEmptyList_whenInputIsNull() {
        // GIVEN - WHEN
        List<RobotInstruction> res = InstructionParser.from(null);
        // THEN
        assertNotNull(res);
        assertTrue(res.isEmpty());
    }

    @Test
    void shouldReturnEmptyList_whenInputIsOnlyWhitespace() {
        // GIVEN - WHEN
        List<RobotInstruction> res = InstructionParser.from("   \t  \n ");
        // THEN
        assertNotNull(res);
        assertTrue(res.isEmpty());
    }

    @Test
    void shouldParseLowerUpperAndMixedCaseEqually() {
        // GIVEN - WHEN
        List<RobotInstruction> lower = InstructionParser.from("lmr");
        List<RobotInstruction> upper = InstructionParser.from("LMR");
        List<RobotInstruction> mixed = InstructionParser.from("LmR");
        // THEN
        assertEquals(upper.size(), lower.size());
        assertEquals(upper.size(), mixed.size());
        for (int i = 0; i < upper.size(); i++) {
            assertEquals(upper.get(i).getClass(), lower.get(i).getClass());
            assertEquals(upper.get(i).getClass(), mixed.get(i).getClass());
        }
    }

    @Test
    void shouldMapEachCharToExpectedSingletonInstruction() {
        // GIVEN - WHEN
        List<RobotInstruction> res = InstructionParser.from("LMR");
        // THEN
        assertSame(TurnLeft.INSTANCE,  res.get(0));
        assertSame(MoveForward.INSTANCE, res.get(1));
        assertSame(TurnRight.INSTANCE, res.get(2));
    }

    @Test
    void shouldProduceUnmodifiableList() {
        // GIVEN - WHEN
        List<RobotInstruction> res = InstructionParser.from("LMR");
        // THEN
        assertThrows(UnsupportedOperationException.class, () -> res.add(MoveForward.INSTANCE));
    }

    @Test
    void shouldThrowOnUnknownInstructionChar() {
        // GIVEN - WHEN - THEN
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> InstructionParser.from("MX"));
        assertTrue(ex.getMessage().contains("Unknown instruction"), "Mensaje debe indicar instrucción desconocida");
    }

    @Test
    void shouldThrowOnInternalWhitespaceBetweenInstructions() {
        // GIVEN - WHEN - THEN
        assertThrows(IllegalArgumentException.class, () -> InstructionParser.from("L M"));
        assertThrows(IllegalArgumentException.class, () -> InstructionParser.from("L\tM"));
    }

    @Test
    void shouldHandleLongSequencesCorrectly() {
        // GIVEN
        String seq = "LMLMLMLMM";
        // WHEN
        List<RobotInstruction> res = InstructionParser.from(seq);
        // THEN
        assertEquals(seq.length(), res.size());
        assertSame(TurnLeft.INSTANCE, res.get(0));
        assertSame(MoveForward.INSTANCE, res.get(1));
        assertSame(MoveForward.INSTANCE, res.get(res.size() - 1));
    }
}
