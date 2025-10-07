package com.vwdigitalhub.robots.application.domain.model.instructions;

import com.vwdigitalhub.robots.application.domain.model.Robot;

public final class TurnLeft implements RobotInstruction {
    public static final TurnLeft INSTANCE = new TurnLeft();

    private TurnLeft() {}

    @Override
    public void apply(Robot robot) {
        robot.turnLeft();
    }
}
