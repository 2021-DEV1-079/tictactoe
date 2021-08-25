package com.example.tictactoe.service.impl;

import com.example.tictactoe.service.FormattingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SimpleFormattingServiceImpl implements FormattingService {
    @Override
    public List<String> format(UUID[][] board, String emptyRepresentation) {
        return null;
    }
}
