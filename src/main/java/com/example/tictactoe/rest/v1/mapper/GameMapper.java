package com.example.tictactoe.rest.v1.mapper;

import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
import com.example.tictactoe.domain.model.GameState;
import com.example.tictactoe.rest.v1.dto.GameMoveDto;
import com.example.tictactoe.rest.v1.dto.GameStateDto;
import com.example.tictactoe.rest.v1.dto.PlayerCredentialsDto;

import java.util.UUID;

public class GameMapper {
    public PlayerCredentialsDto toPlayerCredentialsDto(Game game, UUID playerId) {
        return new PlayerCredentialsDto(game.getGameId(), playerId);
    }

    public GameMove gameMoveDtoToGameMove(GameMoveDto gameMoveDto) {
        return new GameMove(gameMoveDto.getPlayerId(), gameMoveDto.getX(), gameMoveDto.getY());
    }

    public GameStateDto gameStateToGameStateDto(GameState gameState) {
        return new GameStateDto(gameState.getStatus(), gameState.getNextPlayer());
    }
}
