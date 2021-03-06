package com.example.tictactoe.service.impl;

import com.example.tictactoe.service.FormattingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SimpleFormattingServiceImpl implements FormattingService {
    @Override
    public List<String> format(UUID[][] board, UUID p1Id, String emptyRepresentation, String p1Representation, String p2Representation) {
        List<String> formatted = new ArrayList<>();
        for (UUID[] uuids : board) {
            StringBuilder sb = new StringBuilder();
            for (UUID posUuid : uuids) {
                if (posUuid == null) {
                    sb.append(emptyRepresentation);
                } else if (posUuid.equals(p1Id)) {
                    sb.append(p1Representation);
                } else {
                    sb.append(p2Representation);
                }
            }
            formatted.add(sb.toString());
        }
        return formatted;
    }
}
