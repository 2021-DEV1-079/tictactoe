package com.example.tictactoe.rest.v1.mapper;

import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.rest.v1.dto.PlayerCredentialsDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;



public class GameMapperTest {

    private GameMapper gameMapper = new GameMapper();

    @Test
    public void test_mapping_game_to_playerCredentialsDto(){
        Game game = new Game(UUID.randomUUID(), UUID.randomUUID());

        PlayerCredentialsDto pc = gameMapper.toPlayerCredentialsDto(game, game.getPlayerOneId());

        assertEquals(game.getGameId(), pc.getGameId());
    }

}
