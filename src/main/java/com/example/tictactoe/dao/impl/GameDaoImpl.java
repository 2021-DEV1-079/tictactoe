package com.example.tictactoe.dao.impl;

import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.domain.model.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class GameDaoImpl implements GameDao {

    private Map<UUID, Game> simpleStorage = new HashMap<>();

    @Override
    public Game save(Game game) {
        simpleStorage.put(game.getGameId(), game);
        return game;
    }
}
