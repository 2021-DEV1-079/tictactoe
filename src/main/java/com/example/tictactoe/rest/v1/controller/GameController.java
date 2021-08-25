package com.example.tictactoe.rest.v1.controller;

import com.example.tictactoe.domain.TicTacToeException;
import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.rest.v1.dto.GameMoveDto;
import com.example.tictactoe.rest.v1.dto.GameStateDto;
import com.example.tictactoe.rest.v1.dto.PlayerCredentialsDto;
import com.example.tictactoe.rest.v1.mapper.GameMapper;
import com.example.tictactoe.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/game")
public class GameController {

    private final GameService gameService;

    private final GameMapper gameMapper;

    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @PostMapping("/create")
    public PlayerCredentialsDto create() {
        Game newlyCreatedGame = gameService.createNewGame();
        return gameMapper.toPlayerCredentialsDto(newlyCreatedGame, newlyCreatedGame.getPlayerOneId());
    }

    @PutMapping("/{gameUUID}/join")
    public PlayerCredentialsDto join(@PathVariable UUID gameUUID) {
        try {
            Game joinedGame = gameService.addPlayerToGame(gameUUID);
            return gameMapper.toPlayerCredentialsDto(joinedGame, joinedGame.getPlayerTwoId());
        } catch (TicTacToeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping("/{gameUUID}/play")
    public GameStateDto play(@PathVariable UUID gameUUID, @RequestBody GameMoveDto moveToBePlayed) {
        try {
            gameService.play(gameUUID, gameMapper.gameMoveDtoToGameMove(moveToBePlayed));
            return gameMapper.gameStateToGameStateDto(gameService.getGameState(gameUUID));
        } catch (TicTacToeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{gameUUID}/state")
    public GameStateDto state(@PathVariable UUID gameUUID) {
        try {
            return gameMapper.gameStateToGameStateDto(gameService.getGameState(gameUUID));
        } catch (TicTacToeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
