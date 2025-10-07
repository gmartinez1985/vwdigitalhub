package com.vwdigitalhub.robots.application.adapter.in.cli;

import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ControlRobotCommand;
import com.vwdigitalhub.robots.application.domain.model.Direction;
import com.vwdigitalhub.robots.application.domain.model.Position;
import com.vwdigitalhub.robots.application.domain.model.Workspace;
import com.vwdigitalhub.robots.application.domain.model.instructions.RobotInstruction;
import com.vwdigitalhub.robots.application.domain.port.in.ExecuteInstructionsUseCase;
import com.vwdigitalhub.robots.application.domain.service.util.InstructionParser;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class ConsoleAdapter {

    private final ExecuteInstructionsUseCase useCase;

    public void run(InputStream in, PrintStream out) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            Workspace workspace = readWorkspace(br);
            List<ControlRobotCommand> robotsCommands = readRobots(br, workspace);
            robotsCommands.stream()
                .map(useCase::execute)
                .forEach(out::println);
        }
    }

    private Workspace readWorkspace(BufferedReader br) throws IOException {
        String line = readWorkspaceLine(br);
        String[] parts = splitLine(line);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Workspace line must contain exactly 2 integers");
        }
        int maxX = parseInt(parts[0], "maxX");
        int maxY = parseInt(parts[1], "maxY");
        return new Workspace(maxX, maxY);
    }

    private List<ControlRobotCommand> readRobots(BufferedReader br, Workspace workspace) throws IOException {
        List<ControlRobotCommand> robotsCommands = new ArrayList<>();
        while (true) {
            String posLine = nextNonEmptyLine(br);
            if (posLine == null) break;
            String[] robotStartingData = splitLine(posLine);
            if (robotStartingData.length != 3) {
                throw new IllegalArgumentException("Robot position line must be: '<x> <y> <N|E|S|W>'");
            }
            Position initialPosition = new Position(parseInt(robotStartingData[0], "x"), parseInt(robotStartingData[1], "y"));
            Direction direction = Direction.from(robotStartingData[2]);
            List<RobotInstruction> instructions = parseRobotInstructions(br);
            robotsCommands.add(new ControlRobotCommand(workspace, initialPosition, direction, instructions));
        }
        return robotsCommands;
    }

    private static List<RobotInstruction> parseRobotInstructions(BufferedReader br) throws IOException {
        String instrLine = nextNonEmptyLine(br);
        if (instrLine == null) {
            throw new IllegalArgumentException("Missing instruction line for robot");
        }
        return InstructionParser.from(instrLine.trim());
    }

    private static String readWorkspaceLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new IllegalArgumentException("Missing workspace line");
        }
        return line;
    }

    private static String[] splitLine(String line) {
        return line.trim().split("\\s+");
    }

    private static int parseInt(String s, String field) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer for " + field + ": " + s);
        }
    }

    private static String nextNonEmptyLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isBlank()) {
                return line;
            }
        }
        return null;
    }
}
