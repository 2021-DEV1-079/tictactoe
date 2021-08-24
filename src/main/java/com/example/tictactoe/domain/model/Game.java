package com.example.tictactoe.domain.model;

import java.util.UUID;

public class Game {

    private UUID id;
    private UUID playerOneId;
    private UUID playerTwoId;


    public Game(UUID id, UUID playerOneId) {
        this.id = id;
        this.playerOneId = playerOneId;
    }

    public UUID getGameId() {
        return id;
    }

    public UUID getPlayerOneId() {
        return playerOneId;
    }

    public Object getPlayerTwoId() {
        return playerTwoId;
    }
}
