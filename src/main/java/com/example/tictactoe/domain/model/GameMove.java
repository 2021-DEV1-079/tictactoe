package com.example.tictactoe.domain.model;

import java.util.UUID;

public class GameMove {
    private UUID p1Id;
    private int x;
    private int y;

    public GameMove(UUID p1Id, int x, int y) {

        this.p1Id = p1Id;
        this.x = x;
        this.y = y;
    }
}
