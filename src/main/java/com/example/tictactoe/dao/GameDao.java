package com.example.tictactoe.dao;

import com.example.tictactoe.domain.model.Game;

public interface GameDao {
    Game save(Game game);
}
