package com.example.tictactoe.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Test
    public void create_valid_initial_game_id_is_UUID() {
        Game game = gameService.createNewGame();
    }
}
