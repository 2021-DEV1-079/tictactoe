package com.example.tictactoe.service;

import com.example.tictactoe.dao.GameDao;
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
        return gameDao.save(new Game(UUID.randomUUID()));
    }
}
