package com.example.tictactoe.domain.model;

import com.example.tictactoe.domain.TicTacToeException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    private UUID id;
    private UUID playerOneId;
    private UUID playerTwoId;
    private List<GameMove> movesHistory = new ArrayList<>();
    private UUID[][] board;
    private GameStatus gameStatus = GameStatus.awaitingSecondPlayer;

    public Game(UUID id, UUID playerOneId) {
        this(id, playerOneId, 3, 3);
    }

    public Game(UUID id, UUID playerOneId, int width, int height) {
        this.id = id;
        this.playerOneId = playerOneId;
        board = new UUID[width][height];
    }

    public UUID getGameId() {
        return id;
    }

    public UUID getPlayerOneId() {
        return playerOneId;
    }

    public UUID getPlayerTwoId() {
        return playerTwoId;
    }

    public List<GameMove> getMovesHistory() {
        return movesHistory;
    }

    public void setPlayerTwoId(UUID playerTwoId) {
        this.playerTwoId = playerTwoId;
    }

    public void playMove(GameMove gameMove) throws TicTacToeException {
        if(gameMove.getX() >= 0 && gameMove.getX() < board[0].length && gameMove.getY() >= 0 && gameMove.getX() < board.length){
            this.movesHistory.add(gameMove);
            board[gameMove.getX()][gameMove.getY()] = gameMove.getAssociatedPlayerId();
        }else {
            throw new TicTacToeException("gameMove " + gameMove + " doesn't fit on the board.");
        }

    }

    public GameStatus getStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public UUID[][] getBoard() {
        return this.board;
    }
}
