package com.vwdigitalhub.robots.application.domain.model;

public record Position(int x, int y) {
    public Position move(Direction d) {
        return switch (d) {
            case N -> new Position(x, y + 1);
            case E -> new Position(x + 1, y);
            case S -> new Position(x, y - 1);
            case W -> new Position(x - 1, y);
        };
    }
}
