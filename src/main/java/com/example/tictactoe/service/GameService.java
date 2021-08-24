package com.example.tictactoe.service;

import com.example.tictactoe.config.AppConfig;
import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.domain.TicTacToeException;
import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
import com.example.tictactoe.domain.model.GameStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {
    private final GameDao gameDao;
    private AppConfig appConfig;


    public GameService(GameDao gameDao, AppConfig appConfig) {
        this.gameDao = gameDao;
        this.appConfig = appConfig;
    }

    public Game createNewGame() {
        return gameDao.save(new Game(UUID.randomUUID(), UUID.randomUUID()));
    }

    public Game addPlayerToGame(UUID gameId) throws TicTacToeException {
        var game = getGame(gameId);
        if (game.getPlayerTwoId() != null) {
            throw new TicTacToeException("Given gameId: " + gameId + " has already a player2 defined: " + game.getPlayerTwoId());
        }
        game.setPlayerTwoId(UUID.randomUUID());
        game.setGameStatus(GameStatus.running);
        return gameDao.save(game);
    }

    public Game getGame(UUID gameId) throws TicTacToeException {
        Optional<Game> optionalGame = gameDao.getById(gameId);
        if (optionalGame.isPresent()) {
            return optionalGame.get();
        } else {
            throw new TicTacToeException("No game associated with given gameId: " + gameId);
        }
    }

    public boolean isNextMoveValid(UUID gameId, GameMove gameMove) throws TicTacToeException {
        if (arePositionsInvalid(gameMove)) {
            return false;
        }
        var game = getGame(gameId);
        if (game.getMovesHistory().isEmpty()) {
            return gameMove.getAssociatedPlayerId().equals(game.getPlayerOneId());
        }
        return isRightPlayer(gameMove, game) && positionIsEmpty(gameMove, game);
    }

    private boolean isRightPlayer(GameMove gameMove, Game game) {
        return game.getMovesHistory().get(game.getMovesHistory().size() - 1).getAssociatedPlayerId() != gameMove.getAssociatedPlayerId();
    }

    private boolean positionIsEmpty(GameMove gameMove, Game game) {
        return game.getMovesHistory().stream().noneMatch(alreadyPlayedMove -> alreadyPlayedMove.getX() == gameMove.getX() && alreadyPlayedMove.getY() == gameMove.getY());
    }

    private boolean arePositionsInvalid(GameMove gameMove) {
        return gameMove.getX() < 0 || appConfig.getWidth() <= gameMove.getX() || gameMove.getY() < 0 || appConfig.getHeight() <= gameMove.getY();
    }

    public Game play(UUID gameId, GameMove gameMove) throws TicTacToeException {
        var game = getGame(gameId);
        if (game.getPlayerTwoId() == null) {
            throw new TicTacToeException("Still awaiting second player.");
        }
        if (isNextMoveValid(gameId, gameMove)) {
            game.playMove(gameMove);
            updateGameStatus(game);
            return gameDao.save(game);
        } else {
            throw new TicTacToeException("Cannot play invalid move");
        }
    }

    private void updateGameStatus(Game game) {
        int numberOfMoves = game.getMovesHistory().size();
        GameMove lastMove = game.getMovesHistory().get(numberOfMoves - 1);
        GameStatus potentialWinnerStatus = lastMove.getAssociatedPlayerId() == game.getPlayerOneId() ? GameStatus.p1Won : GameStatus.p2Won;
        var gameBoard = game.getBoard();

        if (playerHasWonHorizontal(lastMove, gameBoard)) {
            game.setGameStatus(potentialWinnerStatus);
            return;
        }

        if (playerHasWonVertical(lastMove, gameBoard)) {
            game.setGameStatus(potentialWinnerStatus);
            return;
        }

        if (playerHasWonObliquePositiveSlope(lastMove, gameBoard) || playerHasWonObliqueNegativeSlope(lastMove, gameBoard)) {
            game.setGameStatus(potentialWinnerStatus);
            return;
        }

        if (numberOfMoves == appConfig.getHeight() * appConfig.getWidth()) {
            game.setGameStatus(GameStatus.draw);
        }

    }

    private boolean playerHasWonObliqueNegativeSlope(GameMove lastMove, UUID[][] gameBoard) {
        var playerHasWon = true;
        var streakCount = 0;
        var xOfLastMove = lastMove.getX();
        var yOfLastMove = lastMove.getY();

        while (yOfLastMove >= 0 && xOfLastMove < appConfig.getWidth() && streakCount < appConfig.getStreakLegth() && playerHasWon) {
            playerHasWon = lastMove.getAssociatedPlayerId().equals(gameBoard[xOfLastMove][yOfLastMove]);
            if (playerHasWon) {
                streakCount++;
            }
            xOfLastMove++;
            yOfLastMove--;
        }

        xOfLastMove = lastMove.getX() - 1;
        yOfLastMove = lastMove.getY() + 1;
        playerHasWon = true;
        while (yOfLastMove < appConfig.getHeight() && xOfLastMove >= 0 && streakCount < appConfig.getStreakLegth() && playerHasWon) {
            playerHasWon = lastMove.getAssociatedPlayerId().equals(gameBoard[xOfLastMove][yOfLastMove]);
            if (playerHasWon) {
                streakCount++;
            }
            xOfLastMove--;
            yOfLastMove++;
        }

        return streakCount >= appConfig.getStreakLegth();
    }

    private boolean playerHasWonObliquePositiveSlope(GameMove lastMove, UUID[][] gameBoard) {
        var playerHasWon = true;
        var streakCount = 0;
        var xOfLastMove = lastMove.getX();
        var yOfLastMove = lastMove.getY();

        while (yOfLastMove < appConfig.getHeight() && xOfLastMove < appConfig.getWidth() && streakCount < appConfig.getStreakLegth() && playerHasWon) {
            playerHasWon = lastMove.getAssociatedPlayerId().equals(gameBoard[xOfLastMove][yOfLastMove]);
            if (playerHasWon) {
                streakCount++;
            }
            xOfLastMove++;
            yOfLastMove++;
        }

        xOfLastMove = lastMove.getX() - 1;
        yOfLastMove = lastMove.getY() - 1;
        playerHasWon = true;
        while (yOfLastMove >= 0 && xOfLastMove >= 0 && streakCount < appConfig.getStreakLegth() && playerHasWon) {
            playerHasWon = lastMove.getAssociatedPlayerId().equals(gameBoard[xOfLastMove][yOfLastMove]);
            if (playerHasWon) {
                streakCount++;
            }
            xOfLastMove--;
            yOfLastMove--;
        }

        return streakCount >= appConfig.getStreakLegth();
    }


    private boolean playerHasWonVertical(GameMove lastMove, UUID[][] gameBoard) {
        var playerHasWon = true;
        var streakCount = 0;
        var xOfLastMove = lastMove.getX();
        var y = 0;
        while (y < appConfig.getHeight() && streakCount < appConfig.getStreakLegth() && playerHasWon) {
            playerHasWon = lastMove.getAssociatedPlayerId().equals(gameBoard[xOfLastMove][y]);
            if (playerHasWon) {
                streakCount++;
            }
            y++;
        }
        return playerHasWon;
    }

    private boolean playerHasWonHorizontal(GameMove lastMove, UUID[][] gameBoard) {
        var playerHasWon = true;
        var streakCount = 0;
        var yOfLastMove = lastMove.getY();
        var x = 0;
        while (x < appConfig.getWidth() && streakCount < appConfig.getStreakLegth() && playerHasWon) {
            playerHasWon = lastMove.getAssociatedPlayerId().equals(gameBoard[x][yOfLastMove]);
            if (playerHasWon) {
                streakCount++;
            }
            x++;
        }
        return playerHasWon;
    }
}
