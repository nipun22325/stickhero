package com.example.stickhero;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Character {
    private static final double playerWidth = 50;
    private static final double playerHeight = 60;

    private double x;
    private double y;

    private Image charImage = new Image("file:character.png");

    public boolean isFlipped = false;
    public Character(double x, double y) {
        this.x = x;
        this.y = y - playerHeight;
    }

    public void make(GraphicsContext gc) {
        if (isFlipped) {
            gc.save();
            gc.translate(x, y + playerHeight);
            gc.scale(1, -1);
            gc.drawImage(charImage, -playerWidth / 2, -60, playerWidth, playerHeight);
            gc.restore();
        }
        else {
            gc.drawImage(charImage, x - playerWidth / 2, y, playerWidth, playerHeight);
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void flip() {
        isFlipped = !isFlipped;
    }
}

