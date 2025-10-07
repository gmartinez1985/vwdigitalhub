package com.vwdigitalhub.robots.application.adapter.in.cli;

import com.vwdigitalhub.robots.application.domain.port.in.ExecuteInstructionsUseCase;
import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ExecuteInstructionsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsoleAdapterParameterizedTest {

    @ParameterizedTest(name = "[{index}] ok → {0}")
    @MethodSource("successCases")
    @DisplayName("Should print expected outputs for valid inputs")
    void shouldPrintExpectedOutputs(String description, String input, String expectedOutput) throws Exception {
        // WHEN
        String output = runAdapter(input);
        // THEN
        assertEquals(expectedOutput, output);
    }

    private Stream<Arguments> successCases() {
        return Stream.of(
                Arguments.of(
                        "statement sample",
                        """
                        5 5
                        1 2 N
                        LMLMLMLMM
                        3 3 E
                        MMRMMRMRRM
                        """,
                        """
                        1 3 N
                        5 1 E
                        """
                ),
                Arguments.of(
                        "ignore blank lines between robots",
                        """
                        5 5
        
                        0 0 N
        
                        M
        
                        2 2 E
        
                        RRRR
                        """,
                        """
                        0 1 N
                        2 2 E
                        """
                )
        );
    }

    @ParameterizedTest(name = "[{index}] ko → {0}")
    @MethodSource("errorCases")
    @DisplayName("Should throw IllegalArgumentException for malformed inputs")
    void shouldThrowIllegalArgument_forMalformedInputs(String description, String malformedInput) {
        assertThrows(IllegalArgumentException.class, () -> runAdapter(malformedInput));
    }

    private Stream<Arguments> errorCases() {
        return Stream.of(
                Arguments.of(
                        "missing workspace line",
                        """
                        
                        
                        """
                ),
                Arguments.of(
                        "workspace line not two integers",
                        """
                        5
                        0 0 N
                        M
                        """
                ),
                Arguments.of(
                        "robot position line malformed",
                        """
                        5 5
                        0 0
                        M
                        """
                ),
                Arguments.of(
                        "missing instruction line for robot",
                        """
                        5 5
                        1 1 N
                        """
                ),
                Arguments.of(
                        "invalid direction token",
                        """
                        5 5
                        1 1 X
                        M
                        """
                ),
                Arguments.of(
                        "coordinates are not integers",
                        """
                        5 5
                        A 1 N
                        M
                        """
                )
        );
    }

    private String runAdapter(String input) throws Exception {
        ExecuteInstructionsUseCase useCase = new ExecuteInstructionsService();
        ConsoleAdapter adapter = new ConsoleAdapter(useCase);

        var in = new ByteArrayInputStream(input.getBytes());
        var out = new ByteArrayOutputStream();

        adapter.run(in, new PrintStream(out));
        return out.toString();
    }
}
