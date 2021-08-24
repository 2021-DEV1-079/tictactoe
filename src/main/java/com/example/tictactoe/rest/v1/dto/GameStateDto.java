package com.example.tictactoe.rest.v1.dto;


import com.example.tictactoe.domain.model.GameStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GameStateDto {
    private GameStatus status;
    private List<String> prettyStringBoardView;
    private UUID nextPlayer;

    public GameStateDto(GameStatus status, String prettyStringBoardView, UUID nextPlayer) {
        this.status = status;
        this.prettyStringBoardView = Arrays.asList(prettyStringBoardView.split("\n"));
        this.nextPlayer = nextPlayer;
    }

    public List<String> getPrettyStringBoardView() {
        return prettyStringBoardView;
    }

    public void setPrettyStringBoardView(String prettyStringBoardView) {
        this.prettyStringBoardView = Collections.singletonList(prettyStringBoardView);
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public UUID getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(UUID nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
}
