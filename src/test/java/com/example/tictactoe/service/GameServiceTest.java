package com.example.tictactoe.service;

import com.example.tictactoe.config.AppConfig;
import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.domain.TicTacToeException;
import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameDao gameDao;

    @Spy
    private AppConfig appConfig;

    @Autowired
    @InjectMocks
    private GameService gameService;

    @Test
    public void create_valid_initial_game_id_is_UUID() {
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.createNewGame();

        Pattern pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
        assertTrue(pattern.asPredicate().test(game.getGameId().toString()));
    }

    @Test
    public void create_valid_initial_game_firstPlayerId_is_UUID() {
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.createNewGame();

        Pattern pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
        assertTrue(pattern.asPredicate().test(game.getPlayerOneId().toString()));
    }


    @Test
    public void creates_valid_initial_game_secondPlayerId_is_null() {
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.createNewGame();

        assertNull(game.getPlayerTwoId());
    }

    @Test
    public void creates_valid_initial_game_moves_is_empty() {
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.createNewGame();

        assertTrue(game.getMovesHistory().isEmpty());
    }

    @Test
    public void addPlayer_adds_palyer_to_valid_game() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, UUID.randomUUID())));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.addPlayerToGame(gameId);

        Pattern pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
        assertNotNull(game.getPlayerTwoId());
        assertTrue(pattern.asPredicate().test(game.getPlayerTwoId().toString()));
    }

    @Test
    public void addPlayer_throws_exception_player_already_added() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, UUID.randomUUID())));

        gameService.addPlayerToGame(gameId);
        Exception exception = assertThrows(TicTacToeException.class, () -> {
            gameService.addPlayerToGame(gameId);
        });

        assertTrue(exception.getMessage().contains("Given gameId: " + gameId + " has already a player2 defined:"));
    }

    @Test
    public void addPlayer_throws_exception_no_game_found_given_id() {
        UUID gameId = UUID.randomUUID();
        Mockito.when(gameDao.getById(gameId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TicTacToeException.class, () -> {
            gameService.addPlayerToGame(gameId);
        });

        assertTrue(exception.getMessage().contains("No game associated with given gameId: " + gameId));
    }

    @Test
    public void isNextMoveValid_firstMove_game_exists_valid_positions() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, p1Id)));

        GameMove gameMove = new GameMove(p1Id, 0, 0);
        assertTrue(gameService.isNextMoveValid(gameId, gameMove));
    }

    @Test
    public void isNextMoveValid_firstMove_game_doesnt_exist() {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.empty());

        GameMove gameMove = new GameMove(p1Id, 0, 0);
        Exception exception = assertThrows(TicTacToeException.class, () -> {
            gameService.isNextMoveValid(gameId, gameMove);
        });

        assertTrue(exception.getMessage().contains("No game associated with given gameId: " + gameId));
    }

    @Test
    public void isNextMoveValid_wrong_player_firstMove() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, p1Id)));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.addPlayerToGame(gameId);

        GameMove gameMove = new GameMove(game.getPlayerTwoId(), 0, 0);
        assertFalse(gameService.isNextMoveValid(gameId, gameMove));
    }

    @Test
    public void isNextMoveValid_invalid_x_position_too_high() throws TicTacToeException {
        GameMove gameMove = new GameMove(UUID.randomUUID(), 3, 0);
        assertFalse(gameService.isNextMoveValid(UUID.randomUUID(), gameMove));
    }

    @Test
    public void isNextMoveValid_invalid_x_position_too_low() throws TicTacToeException {
        GameMove gameMove = new GameMove(UUID.randomUUID(), -1, 0);
        assertFalse(gameService.isNextMoveValid(UUID.randomUUID(), gameMove));
    }
}
