package com.example.tictactoe.rest.v1.mapper;

import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
import com.example.tictactoe.domain.model.GameState;
import com.example.tictactoe.domain.model.GameStatus;
import com.example.tictactoe.rest.v1.dto.GameMoveDto;
import com.example.tictactoe.rest.v1.dto.GameStateDto;
import com.example.tictactoe.rest.v1.dto.PlayerCredentialsDto;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


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

    @Test
    public void test_mapping_gameStateToGameStateDto_nextPlayer(){
        UUID playerOneId = UUID.randomUUID();
        Game game = new Game(UUID.randomUUID(), playerOneId);
        game.setPlayerTwoId(UUID.randomUUID());
        game.setGameStatus(GameStatus.running);
        GameState gameState = new GameState(game);

        GameStateDto gameStateDto = gameMapper.gameStateToGameStateDto(gameState);

        assertEquals(gameState.getNextPlayer(), gameStateDto.getNextPlayer());

    }

    @Test
    public void test_mapping_gameStateToGameStateDto_status(){
        UUID playerOneId = UUID.randomUUID();
        Game game = new Game(UUID.randomUUID(), playerOneId);
        game.setPlayerTwoId(UUID.randomUUID());
        game.setGameStatus(GameStatus.running);
        GameState gameState = new GameState(game);

        GameStateDto gameStateDto = gameMapper.gameStateToGameStateDto(gameState);

        assertEquals(game.getStatus(), gameStateDto.getStatus());

    }

}
