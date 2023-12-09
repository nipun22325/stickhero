package com.example.stickhero;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.example.stickhero.MainGameplay.HEIGHT;
import static com.example.stickhero.MainGameplay.WIDTH;

public  class StartScreen extends Pane {
    public StartScreen(Runnable onStart) {
        setPrefSize(WIDTH, HEIGHT);

        Image backgroundImage = new Image("file:background.jpg");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(800, 600, false, false, false, false)
        );
        setBackground(new Background(background));
        Text gameName = new Text("Stick Hero");
        gameName.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        gameName.setFill(Color.BLACK);
        gameName.setTextAlignment(TextAlignment.CENTER);
        gameName.setLayoutX((WIDTH - gameName.getBoundsInLocal().getWidth()) / 2);
        gameName.setLayoutY((double) HEIGHT / 4);

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 24; -fx-background-color: #FF0000;");
        startButton.setOnAction(event -> onStart.run());
        startButton.setLayoutX(gameName.getLayoutX() + 120);
        startButton.setLayoutY((double) HEIGHT / 2);

        Text instructions = new Text("""
                Instructions:
                 1) Hold the right mouse button to extend the stick to next platform.
                 2) Press the alt key to flip the character to restore cherries.
                 3)Press the key S to save the game at any point.
                 4) Press the key R to restore a previously saved game.""");
        instructions.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        instructions.setFill(Color.WHITE);
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setLayoutX((WIDTH - instructions.getBoundsInLocal().getWidth()) / 2);
        instructions.setLayoutY((double) HEIGHT /1.5);

        getChildren().addAll(gameName, startButton, instructions);
    }
}