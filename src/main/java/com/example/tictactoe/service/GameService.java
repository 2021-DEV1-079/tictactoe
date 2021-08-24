package com.example.tictactoe.service;

import com.example.tictactoe.config.AppConfig;
import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.domain.TicTacToeException;
import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
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
        game.playMove(gameMove);
        return gameDao.save(game);
    }
}
