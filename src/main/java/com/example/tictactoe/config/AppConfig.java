package com.example.tictactoe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.tictac")
public class AppConfig {
    int width = 3;
    int height = 3;
    int streakLegth = 3;

    public AppConfig(int width, int height, int streakLegth) {
        this.width = width;
        this.height = height;
        this.streakLegth = streakLegth;
    }

    public AppConfig() {
    }

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

    public int getStreakLegth() {
        return streakLegth;
    }

    public void setStreakLegth(int streakLegth) {
        this.streakLegth = streakLegth;
    }
}
