package com.vwdigitalhub.robots.application.domain.model;

import com.vwdigitalhub.robots.application.domain.model.instructions.RobotInstruction;
import lombok.Getter;

import java.util.Objects;

public final class Robot extends AggregateRoot {
    @Getter
    private Position position;
    @Getter
    private Direction direction;

    public static Robot create(Position position, Direction direction) {
        return new Robot(position, direction);
    }

    private Robot(Position position, Direction direction) {
        super();
        this.position = Objects.requireNonNull(position, "position must not be null");
        this.direction = Objects.requireNonNull(direction, "direction must not be null");
    }

    public Position executeInstruction(RobotInstruction instruction) {
        instruction.apply(this);
        return this.position;
    }

    public void turnLeft()  {
        this.direction = this.direction.turnLeft();
    }

    public void turnRight() {
        this.direction = this.direction.turnRight();
    }

    public void moveForwardOne() {
        this.position = this.position.move(direction);
    }
}
