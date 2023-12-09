package com.example.stickhero;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public  class Platform {
    private double x;
    private double width;
    private double height;

    public double getX() {
        return x;
    }
    public int getX_int(){
        return (int)x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getWidth() {
        return width;
    }
    public double getY() {
        return MainGameplay.HEIGHT - height;
    }
    public double getHeight() {
        return height;
    }

    public Platform(double x, double width, double height) {
        this.x = x;
        this.width = width;
        this.height = height;
    }

    public void make(GraphicsContext gc) {
        gc.setFill(Color.GREY);
        gc.fillRect(x, MainGameplay.HEIGHT - height, width, height);
    }
}