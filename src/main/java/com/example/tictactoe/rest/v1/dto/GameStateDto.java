package com.example.tictactoe.rest.v1.dto;


import com.example.tictactoe.domain.model.GameStatus;

import java.util.List;
import java.util.UUID;

public class GameStateDto {

    private GameStatus status;
    private UUID nextPlayer;
    private List<String> boardRepresentation;


    public GameStateDto(GameStatus status, UUID nextPlayer, List<String> boardRepresentation) {
        this.status = status;
        this.nextPlayer = nextPlayer;
        this.boardRepresentation = boardRepresentation;
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

    public List<String> getBoardRepresentation() {
        return boardRepresentation;
    }

    public void setBoardRepresentation(List<String> boardRepresentation) {
        this.boardRepresentation = boardRepresentation;
    }
}
