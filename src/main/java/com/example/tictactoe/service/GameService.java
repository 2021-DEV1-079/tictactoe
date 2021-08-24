package com.example.tictactoe.service;

import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.domain.TicTacToeException;
import com.example.tictactoe.domain.model.Game;
import org.springframework.stereotype.Service;

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

    private Game getGame(UUID gameId){
        return gameDao.getById(gameId).get();
    }
}
