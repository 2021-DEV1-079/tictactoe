package com.example.tictactoe.dao;

import com.example.tictactoe.domain.model.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameDao {
    Game save(Game game);

    Optional<Game> getById(UUID gameId);
}
