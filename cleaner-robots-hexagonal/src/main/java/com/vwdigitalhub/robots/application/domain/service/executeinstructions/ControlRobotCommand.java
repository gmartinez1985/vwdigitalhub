package com.vwdigitalhub.robots.application.domain.service.executeinstructions;

import com.vwdigitalhub.robots.application.domain.model.Direction;
import com.vwdigitalhub.robots.application.domain.model.Position;
import com.vwdigitalhub.robots.application.domain.model.Workspace;
import com.vwdigitalhub.robots.application.domain.model.instructions.RobotInstruction;

import java.util.List;

public record ControlRobotCommand(Workspace workspace, Position start, Direction direction, List<RobotInstruction> instructions) {}
