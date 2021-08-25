package com.example.tictactoe.service;

import com.example.tictactoe.service.impl.SimpleFormattingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class FormattingServiceTest {

    @Autowired
    @InjectMocks
    private SimpleFormattingServiceImpl formattingService;

    @Test
    public void test_formatting_emptyBoard() {
        UUID[][] board = new UUID[3][3];

        var formatted = formattingService.format(board, UUID.randomUUID(), ".", ".");

        assertEquals("...", formatted.get(0));
        assertEquals("...", formatted.get(1));
        assertEquals("...", formatted.get(2));
    }

    @Test
    public void test_formatting_player1_only_played() {
        UUID[][] board = new UUID[3][3];
        UUID p1Id = UUID.randomUUID();
        board[0][0] = p1Id;

        var formatted = formattingService.format(board, p1Id, ".", "x");

        assertEquals("x..", formatted.get(0));
        assertEquals("...", formatted.get(1));
        assertEquals("...", formatted.get(2));
    }

}
