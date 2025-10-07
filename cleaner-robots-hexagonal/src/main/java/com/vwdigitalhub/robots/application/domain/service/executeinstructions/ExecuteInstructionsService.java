package com.vwdigitalhub.robots.application.domain.service.executeinstructions;

import com.vwdigitalhub.robots.application.domain.model.Robot;
import com.vwdigitalhub.robots.application.domain.port.in.ExecuteInstructionsUseCase;

public class ExecuteInstructionsService implements ExecuteInstructionsUseCase {

    @Override
    public ControlRobotCommandResponse execute(ControlRobotCommand command) {
        var workspace = command.workspace();
        workspace.ensureValidPosition(command.start());
        var robot = Robot.create(command.start(), command.direction());
        command.instructions().forEach(instruction ->
            workspace.ensureValidPosition(robot.executeInstruction(instruction))
        );
        workspace.occupy(robot.getPosition());
        return new ControlRobotCommandResponse(robot.getPosition(), robot.getDirection());
    }
}
