package com.vwdigitalhub.robots.application.domain.exception;

import com.vwdigitalhub.robots.application.domain.model.Position;

public class OutOfBoundsException extends DomainException {
    public OutOfBoundsException(Position position) {
        super("Position out of bounds: " + position);
    }
}
