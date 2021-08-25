package com.example.tictactoe.service;

import java.util.List;
import java.util.UUID;

public interface FormattingService {
    List<String> format(UUID[][] board, UUID p1Id, String emptyRepresentation, String p1Representation);
}
