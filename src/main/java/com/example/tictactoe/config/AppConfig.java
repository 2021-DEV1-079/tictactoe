package com.example.tictactoe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.tictac")
public class AppConfig {
    int width = 3;
    int height = 3;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
