package com.example.tictactoe.service;

import java.util.List;
import java.util.UUID;

public interface FormattingService {
    List<String> format(UUID[][] board, String emptyRepresentation);
}
