package com.example.tictactoe.service;

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

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
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
        var game = getGame(gameId);
        return game.getMovesHistory().isEmpty();
    }
}
