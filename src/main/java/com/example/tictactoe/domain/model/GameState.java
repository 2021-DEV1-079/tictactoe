package com.example.tictactoe.domain.model;

import java.util.List;
import java.util.UUID;

public class GameState {
    private UUID nextPlayer;
    private GameStatus status;
    private List<String> boardRepresentation;


    public GameState(Game game, List<String> boardRepresentation) {
        this.status = game.getStatus();
        this.boardRepresentation = boardRepresentation;
        if (game.getStatus() == GameStatus.running) {
            if(game.getMovesHistory().isEmpty()){
                nextPlayer = game.getPlayerOneId();
            }else {
                UUID lastMovePlayerID = game.getMovesHistory().get(game.getMovesHistory().size() - 1).getAssociatedPlayerId();
                nextPlayer = game.getPlayerOneId().equals(lastMovePlayerID)  ? game.getPlayerTwoId() : game.getPlayerOneId();
            }
        }
    }

    public List<String> getBoardRepresentation() {
        return boardRepresentation;
    }

    public UUID getNextPlayer() {
        return nextPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }
}
