package com.example.tictactoe.service;

import com.example.tictactoe.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Autowired
    @InjectMocks
    private GameService gameService;

    @Test
    public void create_valid_initial_game_id_is_UUID() {
        Game game = gameService.createNewGame();

        Pattern pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
        assertTrue(pattern.asPredicate().test(game.getGameId().toString()));
    }
}
