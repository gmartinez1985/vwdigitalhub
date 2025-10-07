package com.vwdigitalhub.robots.application.domain.model.instructions;

import com.vwdigitalhub.robots.application.domain.model.Robot;

public sealed interface RobotInstruction permits TurnLeft, TurnRight, MoveForward {
    void apply(Robot robot);
}
