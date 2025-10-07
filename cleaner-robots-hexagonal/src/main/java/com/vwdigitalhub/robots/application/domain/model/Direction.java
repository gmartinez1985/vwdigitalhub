package com.vwdigitalhub.robots.application.domain.model;

public enum Direction {
    N, E, S, W;

    public Direction turnLeft() {
        return switch (this) {
            case N -> W;
            case W -> S;
            case S -> E;
            case E -> N;
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }

    public static Direction from(String token) {
        if (token == null || token.length() != 1) {
            throw new IllegalArgumentException("Direction must be one of N,E,S,W");
        }
        return from(token.charAt(0));
    }

    public static Direction from(char ch) {
        return switch (Character.toUpperCase(ch)) {
            case 'N' -> N;
            case 'E' -> E;
            case 'S' -> S;
            case 'W' -> W;
            default  -> throw new IllegalArgumentException("Direction must be one of N,E,S,W");
        };
    }
}
