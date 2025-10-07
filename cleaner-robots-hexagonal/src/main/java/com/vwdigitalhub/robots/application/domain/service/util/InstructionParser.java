package com.vwdigitalhub.robots.application.domain.service.util;

import com.vwdigitalhub.robots.application.domain.model.instructions.MoveForward;
import com.vwdigitalhub.robots.application.domain.model.instructions.RobotInstruction;
import com.vwdigitalhub.robots.application.domain.model.instructions.TurnLeft;
import com.vwdigitalhub.robots.application.domain.model.instructions.TurnRight;

import java.util.List;

public final class InstructionParser {
    private InstructionParser() {}

    private static RobotInstruction from(char ch) {
        return switch (Character.toUpperCase(ch)) {
            case 'L' -> TurnLeft.INSTANCE;
            case 'R' -> TurnRight.INSTANCE;
            case 'M' -> MoveForward.INSTANCE;
            default  -> throw new IllegalArgumentException("Unknown instruction: " + ch);
        };
    }

    public static List<RobotInstruction> from(String raw) {
        if (raw == null) return List.of();
        return raw.trim()
            .chars()
            .mapToObj(c -> from((char) c))
            .toList();
    }
}
