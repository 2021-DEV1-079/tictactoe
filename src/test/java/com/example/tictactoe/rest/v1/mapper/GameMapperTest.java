package com.example.tictactoe.rest.v1.mapper;

import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
import com.example.tictactoe.rest.v1.dto.GameMoveDto;
import com.example.tictactoe.rest.v1.dto.PlayerCredentialsDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;



public class GameMapperTest {

    private GameMapper gameMapper = new GameMapper();

    @Test
    public void test_mapping_game_to_playerCredentialsDto(){
        Game game = new Game(UUID.randomUUID(), UUID.randomUUID());

        UUID playerOneId = game.getPlayerOneId();
        PlayerCredentialsDto pc = gameMapper.toPlayerCredentialsDto(game, playerOneId);

        assertEquals(game.getGameId(), pc.getGameId());
        assertEquals(playerOneId, pc.getPlayerId());
    }

    @Test
    public void test_mapping_gameMoveDtoToGameMove(){
        UUID associatedPlayerId = UUID.randomUUID();
        int x = 1;
        int y = 2;
        GameMoveDto gameMoveDto = new GameMoveDto(associatedPlayerId, x, y);

        GameMove gameMove = gameMapper.gameMoveDtoToGameMove(gameMoveDto);

        assertEquals(gameMove.getAssociatedPlayerId(), associatedPlayerId);
        assertEquals(gameMove.getX(), x);
        assertEquals(gameMove.getY(), y);

    }

}
