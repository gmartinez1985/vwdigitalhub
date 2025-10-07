package com.vwdigitalhub.robots.application.domain.model.instructions;

import com.vwdigitalhub.robots.application.domain.model.Robot;

public final class TurnRight implements RobotInstruction {
    public static final TurnRight INSTANCE = new TurnRight();

    private TurnRight() {}

    @Override
    public void apply(Robot robot) {
        robot.turnRight();
    }
}
