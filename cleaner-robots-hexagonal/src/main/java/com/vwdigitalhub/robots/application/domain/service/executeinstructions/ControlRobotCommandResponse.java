package com.vwdigitalhub.robots.application.domain.service.executeinstructions;

import com.vwdigitalhub.robots.application.domain.model.Direction;
import com.vwdigitalhub.robots.application.domain.model.Position;

public record ControlRobotCommandResponse(Position finalPosition, Direction finalDirection) {

    @Override
    public String toString() {
        return finalPosition.x() + " " + finalPosition.y() + " " + finalDirection.name();
    }
}
