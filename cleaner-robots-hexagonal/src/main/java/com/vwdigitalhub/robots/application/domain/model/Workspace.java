package com.vwdigitalhub.robots.application.domain.model;

import com.vwdigitalhub.robots.application.domain.exception.InvalidWorkspaceException;
import com.vwdigitalhub.robots.application.domain.exception.OutOfBoundsException;
import com.vwdigitalhub.robots.application.domain.exception.PositionOccupiedException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Workspace extends AggregateRoot {
    private final int maxX;
    private final int maxY;
    private final Set<Position> positionsOccupied = new HashSet<>();

    public Workspace(int maxX, int maxY) {
        super();
        if (maxX < 0 || maxY < 0) {
            throw new InvalidWorkspaceException("Workspace bounds must be non-negative: maxX=" + maxX + ", maxY=" + maxY);
        }
        this.maxX = maxX;
        this.maxY = maxY;
    }

    private boolean isInside(Position p) {
        Objects.requireNonNull(p, "position must not be null");
        return p.x() >= 0 && p.y() >= 0 && p.x() <= maxX && p.y() <= maxY;
    }

    public void ensureValidPosition(Position p) {
        if (!isInside(p)) throw new OutOfBoundsException(p);
        if (positionsOccupied.contains(p)) throw new PositionOccupiedException(p);
    }

    public void occupy(Position p) {
        this.ensureValidPosition(p);
        positionsOccupied.add(p);
    }
}
