package com.example.tictactoe.domain.model;

import java.util.UUID;

public class GameState {
    private UUID nextPlayer;
    private GameStatus status;


    public GameState(Game game) {
        this.status = game.getStatus();
        if (game.getStatus() == GameStatus.running) {
            if(game.getMovesHistory().isEmpty()){
                nextPlayer = game.getPlayerOneId();
            }else {
                nextPlayer = game.getMovesHistory().get(game.getMovesHistory().size() - 1).getAssociatedPlayerId() == game.getPlayerOneId() ? game.getPlayerTwoId() : game.getPlayerOneId();
            }
        }
    }

    public UUID getNextPlayer() {
        return nextPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }
}
