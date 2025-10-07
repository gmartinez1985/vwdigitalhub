package com.vwdigitalhub.robots.application.domain.model;

import com.vwdigitalhub.robots.application.domain.exception.InvalidWorkspaceException;
import com.vwdigitalhub.robots.application.domain.exception.OutOfBoundsException;
import com.vwdigitalhub.robots.application.domain.exception.PositionOccupiedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceAggregateTest {

    @Test
    void shouldAcceptOriginAsValidPosition() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position origin = new Position(0, 0);
        // WHEN + THEN
        assertDoesNotThrow(() -> ws.ensureValidPosition(origin));
    }

    @Test
    void shouldAcceptUpperRightBoundaryAsValidPosition() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position upperRight = new Position(5, 5);
        // WHEN + THEN
        assertDoesNotThrow(() -> ws.ensureValidPosition(upperRight));
    }

    @Test
    void shouldOccupyFreePosition() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(3, 4);
        // WHEN + THEN
        assertDoesNotThrow(() -> ws.occupy(p));
    }

    @Test
    void shouldThrowWhenMaxXIsNegative() {
        // GIVEN + WHEN + THEN
        assertThrows(InvalidWorkspaceException.class, () -> new Workspace(-1, 5));
    }

    @Test
    void shouldThrowWhenMaxYIsNegative() {
        // GIVEN + WHEN + THEN
        assertThrows(InvalidWorkspaceException.class, () -> new Workspace(5, -1));
    }

    @Test
    void shouldThrowWhenPositionXBelowZero() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(-1, 0);
        // WHEN + THEN
        assertThrows(OutOfBoundsException.class, () -> ws.ensureValidPosition(p));
    }

    @Test
    void shouldThrowWhenPositionYBelowZero() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(0, -1);
        // WHEN + THEN
        assertThrows(OutOfBoundsException.class, () -> ws.ensureValidPosition(p));
    }

    @Test
    void shouldThrowWhenPositionXAboveMax() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(6, 0);
        // WHEN + THEN
        assertThrows(OutOfBoundsException.class, () -> ws.ensureValidPosition(p));
    }

    @Test
    void shouldThrowWhenPositionYAboveMax() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(0, 6);
        // WHEN + THEN
        assertThrows(OutOfBoundsException.class, () -> ws.ensureValidPosition(p));
    }

    @Test
    void shouldThrowWhenOccupyingOutOfBounds() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(9, 9);
        // WHEN + THEN
        assertThrows(OutOfBoundsException.class, () -> ws.occupy(p));
    }

    @Test
    void shouldThrowWhenOccupyingSamePositionTwice() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(1, 1);
        // WHEN
        assertDoesNotThrow(() -> ws.occupy(p));
        // THEN
        assertThrows(PositionOccupiedException.class, () -> ws.occupy(p));
    }

    @Test
    void shouldThrowWhenValidatingAlreadyOccupiedPosition() {
        // GIVEN
        Workspace ws = new Workspace(5, 5);
        Position p = new Position(2, 2);
        // WHEN
        assertDoesNotThrow(() -> ws.occupy(p));
        // THEN
        assertThrows(PositionOccupiedException.class, () -> ws.ensureValidPosition(p));
    }
}
