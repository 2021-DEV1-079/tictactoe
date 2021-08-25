package com.example.tictactoe.service;

import com.example.tictactoe.domain.TicTacToeException;
import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
import com.example.tictactoe.domain.model.GameState;

import java.util.UUID;

public interface GameService {
    Game createNewGame();

    Game addPlayerToGame(UUID gameId) throws TicTacToeException;

    Game getGame(UUID gameId) throws TicTacToeException;

    boolean isNextMoveValid(UUID gameId, GameMove gameMove) throws TicTacToeException;

    Game play(UUID gameId, GameMove gameMove) throws TicTacToeException;

    GameState getGameState(UUID gameId) throws TicTacToeException;
}
