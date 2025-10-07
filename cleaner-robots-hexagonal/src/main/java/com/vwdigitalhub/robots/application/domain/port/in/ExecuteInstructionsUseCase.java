package com.vwdigitalhub.robots.application.domain.port.in;

import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ControlRobotCommand;
import com.vwdigitalhub.robots.application.domain.service.executeinstructions.ControlRobotCommandResponse;

public interface ExecuteInstructionsUseCase {
    ControlRobotCommandResponse execute(ControlRobotCommand command);
}
