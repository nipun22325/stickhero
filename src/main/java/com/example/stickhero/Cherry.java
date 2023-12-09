package com.example.stickhero;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Cherry {
    private double x;
    private double y;
    private Image cherry;

    public Cherry(double x, double y) {
        this.x = x;
        this.y = y;
        this.cherry = new Image("file:cherry.png");
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public void make(GraphicsContext gc) {
        gc.drawImage(this.cherry,x, y, 20, 20);
    }
}