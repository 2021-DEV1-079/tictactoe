package com.example.tictactoe.rest.v1.mapper;

import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.rest.v1.dto.PlayerCredentialsDto;

import java.util.UUID;

public class GameMapper {
    public PlayerCredentialsDto toPlayerCredentialsDto(Game game, UUID playerId) {
        return new PlayerCredentialsDto(game.getGameId(), playerId);
    }
}
