package com.example.tictactoe.domain.model;

import java.util.UUID;

public class Game {

    private UUID id;

    public Game(UUID id) {
        this.id = id;
    }

    public UUID getGameId() {
        return id;
    }
}
