package com.vwdigitalhub.robots.application.domain.model.instructions;

import com.vwdigitalhub.robots.application.domain.model.Robot;

public final class MoveForward implements RobotInstruction {
    public static final MoveForward INSTANCE = new MoveForward();

    private MoveForward() {}

    @Override
    public void apply(Robot robot) {
        robot.moveForwardOne();
    }
}
