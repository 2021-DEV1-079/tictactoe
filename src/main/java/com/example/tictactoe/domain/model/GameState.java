package com.example.tictactoe.domain.model;

import java.util.UUID;

public class GameState {
    private UUID nextPlayer;


    public GameState(Game game) {
        if (game.getStatus() == GameStatus.running) {
                nextPlayer = game.getPlayerOneId();
        }
    }

    public UUID getNextPlayer() {
        return nextPlayer;
    }
}
