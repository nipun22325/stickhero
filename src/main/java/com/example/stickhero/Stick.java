package com.example.stickhero;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Stick {
    private double x;
    private double height;
    private Color color;
    private double rotateAngle; 
    private boolean rotationComplete;
    private int stickNum;


    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }
    public boolean isRotationComplete() {
        return rotationComplete;
    }
    public double getTopX() {
        return x + 5;
    }
    public double getTopY() {
        return MainGameplay.HEIGHT - 10 - height;
    }
    public double getHeight(){
        return height;
    }

    public int getStickNum() {
        return stickNum;
    }
    public double getRotateAngle() {
        return rotateAngle;
    }

    public Stick(double x, double height, Color color, int stickNum) {
        this.x = x;
        this.height = height;
        this.color = color;
        this.rotateAngle = 0.0;
        this.rotationComplete = false;
        this.stickNum=stickNum;
    }

    public void make(GraphicsContext gc) {
        gc.save();
        gc.translate(x + 5, MainGameplay.HEIGHT - 100);
        gc.rotate(rotateAngle);
        gc.setFill(color);
        gc.fillRect(-5, -height, 10, height);
        gc.restore();
    }

    public void update() {
        if (!rotationComplete) {
            rotateAngle += 0.5;
        }
        if (rotateAngle >= 90.0) {
            rotateAngle = 90.0;
        }
    }
}