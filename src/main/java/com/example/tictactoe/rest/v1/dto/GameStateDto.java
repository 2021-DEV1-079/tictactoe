package com.example.tictactoe.rest.v1.dto;


import com.example.tictactoe.domain.model.GameStatus;

import java.util.UUID;

public class GameStateDto {

    private UUID nextPlayer;

    public GameStateDto(UUID nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public UUID getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(UUID nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public GameStatus getStatus() {
        return null;
    }
}
