package com.example.tictactoe.domain.model;

import java.util.UUID;

public class GameMove {
    private UUID associatedPlayerId;
    private int x;
    private int y;

    public GameMove(UUID associatedPlayerId, int x, int y) {

        this.associatedPlayerId = associatedPlayerId;
        this.x = x;
        this.y = y;
    }

    public UUID getAssociatedPlayerId() {
        return associatedPlayerId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
