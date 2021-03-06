package com.example.tictactoe.service;

import com.example.tictactoe.config.AppConfig;
import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.domain.TicTacToeException;
import com.example.tictactoe.domain.model.Game;
import com.example.tictactoe.domain.model.GameMove;
import com.example.tictactoe.domain.model.GameStatus;
import com.example.tictactoe.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameDao gameDao;

    @Mock
    private FormattingService formattingService;

    @Spy
    private AppConfig appConfig;

    @Autowired
    @InjectMocks
    private GameServiceImpl gameService;

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

    @Test
    public void isNextMoveValid_invalid_y_position_too_high() throws TicTacToeException {
        GameMove gameMove = new GameMove(UUID.randomUUID(), 0, 3);
        assertFalse(gameService.isNextMoveValid(UUID.randomUUID(), gameMove));
    }

    @Test
    public void isNextMoveValid_invalid_y_position_too_low() throws TicTacToeException {
        GameMove gameMove = new GameMove(UUID.randomUUID(), 0, -1);
        assertFalse(gameService.isNextMoveValid(UUID.randomUUID(), gameMove));
    }

    @Test
    public void play_game_move_added() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, p1Id)));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        gameService.addPlayerToGame(gameId);

        GameMove gameMove = new GameMove(p1Id, 0, 0);

        List<GameMove> moves = gameService.play(gameId, gameMove).getMovesHistory();
        assertEquals(moves.get(moves.size() - 1), gameMove);
    }

    @Test
    public void play_game_not_existant() {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.empty());

        GameMove gameMove = new GameMove(p1Id, 0, 0);

        Exception exception = assertThrows(TicTacToeException.class, () -> {
            gameService.play(gameId, gameMove).getMovesHistory();
        });

        assertTrue(exception.getMessage().contains("No game associated with given gameId: " + gameId));
    }

    @Test
    public void play_game_no_secondPlayerYet() {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, p1Id)));

        GameMove gameMove = new GameMove(p1Id, 0, 0);

        Exception exception = assertThrows(TicTacToeException.class, () -> {
            gameService.play(gameId, gameMove);
        });

        assertTrue(exception.getMessage().contains("Still awaiting second player."));

    }

    @Test
    public void isNextMoveValid_second_move() throws TicTacToeException {

        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        game.playMove(new GameMove(p1Id, 0, 0));
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));

        GameMove gameMove = new GameMove(game.getPlayerTwoId(), 1, 0);

        assertTrue(gameService.isNextMoveValid(gameId, gameMove));
    }

    @Test
    public void isNextMoveValid_second_move_wrong_player() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        game.playMove(new GameMove(p1Id, 0, 0));
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));

        GameMove gameMove = new GameMove(p1Id, 1, 0);

        assertFalse(gameService.isNextMoveValid(gameId, gameMove));
    }

    @Test
    public void isNextMoveValid_second_move_position_already_taken() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        game.playMove(new GameMove(p1Id, 0, 0));
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        game = gameService.addPlayerToGame(gameId);

        GameMove gameMove = new GameMove(game.getPlayerTwoId(), 0, 0);

        assertFalse(gameService.isNextMoveValid(gameId, gameMove));
    }


    @Test
    public void getStatus_awaiting_second_player() {
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.createNewGame();

        assertEquals(GameStatus.awaitingSecondPlayer, game.getStatus());
    }

    @Test
    public void getStatus_is_running_after_player2_joined() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, UUID.randomUUID())));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Game game = gameService.addPlayerToGame(gameId);

        assertEquals(GameStatus.running, game.getStatus());
    }

    @Test
    public void getStatus_is_running_after_move_played() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        game = gameService.addPlayerToGame(gameId);

        GameMove gameMove = new GameMove(p1Id, 0, 0);
        game = gameService.play(gameId, gameMove);

        assertEquals(GameStatus.running, game.getStatus());
    }

    @Test
    public void getStatus_is_won_by_player1_horizontal() throws TicTacToeException {

        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        game = gameService.addPlayerToGame(gameId);
        var p2Id = game.getPlayerTwoId();

        GameMove p1m1 = new GameMove(p1Id, 0, 0);
        GameMove p1m2 = new GameMove(p1Id, 1, 0);
        GameMove p1m3 = new GameMove(p1Id, 2, 0);

        GameMove p2m1 = new GameMove(p2Id, 0, 1);
        GameMove p2m2 = new GameMove(p2Id, 1, 1);

        game = gameService.play(gameId, p1m1);
        game = gameService.play(gameId, p2m1);
        game = gameService.play(gameId, p1m2);
        game = gameService.play(gameId, p2m2);
        game = gameService.play(gameId, p1m3);

        assertEquals(GameStatus.p1Won, game.getStatus());
    }

    @Test
    public void getStatus_is_won_by_player1_vertical() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        game = gameService.addPlayerToGame(gameId);
        var p2Id = game.getPlayerTwoId();

        GameMove p1m1 = new GameMove(p1Id, 1, 0);
        GameMove p1m2 = new GameMove(p1Id, 1, 1);
        GameMove p1m3 = new GameMove(p1Id, 1, 2);

        GameMove p2m1 = new GameMove(p2Id, 0, 1);
        GameMove p2m2 = new GameMove(p2Id, 0, 2);

        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        game = gameService.play(gameId, p1m1);
        game = gameService.play(gameId, p2m1);
        game = gameService.play(gameId, p1m2);
        game = gameService.play(gameId, p2m2);
        game = gameService.play(gameId, p1m3);

        assertEquals(GameStatus.p1Won, game.getStatus());
    }

    @Test
    public void gettStatus_is_won_by_player1_oblique_positive_slope() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        game = gameService.addPlayerToGame(gameId);
        var p2Id = game.getPlayerTwoId();

        GameMove p1m1 = new GameMove(p1Id, 0, 0);
        GameMove p1m2 = new GameMove(p1Id, 1, 1);
        GameMove p1m3 = new GameMove(p1Id, 2, 2);

        GameMove p2m1 = new GameMove(p2Id, 0, 1);
        GameMove p2m2 = new GameMove(p2Id, 0, 2);

        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        game = gameService.play(gameId, p1m1);
        game = gameService.play(gameId, p2m1);
        game = gameService.play(gameId, p1m2);
        game = gameService.play(gameId, p2m2);
        game = gameService.play(gameId, p1m3);

        assertEquals(GameStatus.p1Won, game.getStatus());
    }

    @Test
    public void gettStatus_is_won_by_player1_oblique_negative_slope() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        game = gameService.addPlayerToGame(gameId);
        var p2Id = game.getPlayerTwoId();

        GameMove p1m1 = new GameMove(p1Id, 0, 2);
        GameMove p1m2 = new GameMove(p1Id, 1, 1);
        GameMove p1m3 = new GameMove(p1Id, 2, 0);

        GameMove p2m1 = new GameMove(p2Id, 0, 1);
        GameMove p2m2 = new GameMove(p2Id, 0, 0);

        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        game = gameService.play(gameId, p1m1);
        game = gameService.play(gameId, p2m1);
        game = gameService.play(gameId, p1m2);
        game = gameService.play(gameId, p2m2);
        game = gameService.play(gameId, p1m3);

        assertEquals(GameStatus.p1Won, game.getStatus());
    }

    @Test
    public void getStatus_is_draw() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1Id = UUID.randomUUID();
        Game game = new Game(gameId, p1Id);
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        game = gameService.addPlayerToGame(gameId);
        var p2Id = game.getPlayerTwoId();

        GameMove p1m1 = new GameMove(p1Id, 0, 0);
        GameMove p2m1 = new GameMove(p2Id, 1, 0);
        GameMove p1m2 = new GameMove(p1Id, 2, 0);

        GameMove p2m2 = new GameMove(p2Id, 0, 1);
        GameMove p1m3 = new GameMove(p1Id, 1, 1);
        GameMove p2m3 = new GameMove(p2Id, 0, 2);

        GameMove p1m4 = new GameMove(p1Id, 2, 1);
        GameMove p2m4 = new GameMove(p2Id, 2, 2);
        GameMove p1m5 = new GameMove(p1Id, 1, 2);


        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());

        game = gameService.play(gameId, p1m1);
        game = gameService.play(gameId, p2m1);
        game = gameService.play(gameId, p1m2);
        game = gameService.play(gameId, p2m2);
        game = gameService.play(gameId, p1m3);
        game = gameService.play(gameId, p2m3);
        game = gameService.play(gameId, p1m4);
        game = gameService.play(gameId, p2m4);
        game = gameService.play(gameId, p1m5);

        assertEquals(GameStatus.draw, game.getStatus());
    }

    @Test
    public void getGamestate_next_player_is_first_when_game_ready() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1 = UUID.randomUUID();
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(new Game(gameId, p1)));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        gameService.addPlayerToGame(gameId);

        assertEquals(p1, gameService.getGameState(gameId).getNextPlayer());
    }


    @Test
    public void getGamestate_next_player_is_second_after_firstMove() throws TicTacToeException {
        UUID gameId = UUID.randomUUID();
        UUID p1 = UUID.randomUUID();
        Game game = new Game(gameId, p1);
        Mockito.when(gameDao.getById(any())).thenReturn(Optional.of(game));
        Mockito.when(gameDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
        game = gameService.addPlayerToGame(gameId);
        var p2 = game.getPlayerTwoId();

        GameMove p1m1 = new GameMove(p1, 0, 0);
        game = gameService.play(gameId, p1m1);

        assertEquals(p2, gameService.getGameState(gameId).getNextPlayer());
    }

}
