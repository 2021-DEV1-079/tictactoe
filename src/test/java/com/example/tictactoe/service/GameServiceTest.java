package com.example.tictactoe.service;

import com.example.tictactoe.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Autowired
    @InjectMocks
    private GameService gameService;

    @Test
    public void create_valid_initial_game_id_is_UUID() {
        Game game = gameService.createNewGame();
    }
}
