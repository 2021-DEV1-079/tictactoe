package com.example.tictactoe.rest.v1.dto;


import com.example.tictactoe.domain.model.GameStatus;

import java.util.UUID;

public class GameStateDto {

    private GameStatus status;
    private UUID nextPlayer;

    public GameStateDto(GameStatus status, UUID nextPlayer) {
        this.status = status;
        this.nextPlayer = nextPlayer;
    }

    public UUID getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(UUID nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
