package com.example.tictactoe.rest.v1.dto;

import java.util.UUID;

public class PlayerCredentialsDto {
    private UUID gameId;
    private UUID playerId;

    public PlayerCredentialsDto(UUID gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
