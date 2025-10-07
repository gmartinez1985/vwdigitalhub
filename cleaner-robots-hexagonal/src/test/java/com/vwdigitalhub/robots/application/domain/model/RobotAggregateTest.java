package com.vwdigitalhub.robots.application.domain.model;

import com.vwdigitalhub.robots.application.domain.model.instructions.MoveForward;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotAggregateTest {

    @Test
    void shouldCreateRobotWithPositionAndDirection() {
        // GIVEN
        Position start = new Position(1, 1);
        // WHEN
        Robot r = Robot.create(start, Direction.N);
        // THEN
        assertEquals(start, r.getPosition());
        assertEquals(Direction.N, r.getDirection());
    }

    @Test
    void shouldTurnLeft() {
        // GIVEN
        Robot r = Robot.create(new Position(2, 2), Direction.N);
        // WHEN
        r.turnLeft();
        // THEN
        assertEquals(Direction.W, r.getDirection());
    }

    @Test
    void shouldTurnRight() {
        // GIVEN
        Robot r = Robot.create(new Position(2, 2), Direction.N);
        // WHEN
        r.turnRight();
        // THEN
        assertEquals(Direction.E, r.getDirection());
    }

    @Test
    void shouldMoveToUpdatePosition() {
        // GIVEN
        Robot r = Robot.create(new Position(0, 0), Direction.E);
        Position next = new Position(1, 0);
        // WHEN
        r.moveForwardOne();
        // THEN
        assertEquals(next, r.getPosition());
        assertEquals(Direction.E, r.getDirection());
    }

    @Test
    void shouldExecuteInstruction_MoveForward_returnNewPosition_andMutate() {
        // GIVEN
        Robot r = Robot.create(new Position(1, 1), Direction.N);
        // WHEN
        Position returned = r.executeInstruction(MoveForward.INSTANCE);
        // THEN
        assertEquals(new Position(1, 2), returned);
        assertEquals(new Position(1, 2), r.getPosition());
    }
}
