package com.example.tictactoe.service;

import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameDao gameDao;

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
}
