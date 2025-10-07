package com.vwdigitalhub.robots.application.domain.exception;

import com.vwdigitalhub.robots.application.domain.model.Position;

public class PositionOccupiedException extends DomainException {
    public PositionOccupiedException(Position position) {
        super("Final position already occupied by another robot: " + position);
    }
}
