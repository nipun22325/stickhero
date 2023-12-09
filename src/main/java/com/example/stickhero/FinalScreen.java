package com.example.stickhero;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.example.stickhero.MainGameplay.HEIGHT;
import static com.example.stickhero.MainGameplay.WIDTH;

public class FinalScreen extends Pane {
    public FinalScreen(int score, int cherriesCollected, int highestScore, Runnable onRestartCallback) {
        setPrefSize(WIDTH, HEIGHT);

        Text endText = new Text("Game Over\nScore: " + score+"\nCherries Collected: " + cherriesCollected+"\nHighest Score: " + highestScore);
        endText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        endText.setFill(Color.WHITE);
        endText.setTextAlignment(TextAlignment.CENTER);
        endText.setLayoutX((WIDTH - endText.getBoundsInLocal().getWidth()) / 2);
        endText.setLayoutY((double) HEIGHT / 3);

        Button restartButton = new Button("Restart Game");
        restartButton.setStyle("-fx-font-size: 24; -fx-background-color: #00FF00;");
        restartButton.setOnAction(event -> onRestartCallback.run());
        restartButton.setLayoutX((WIDTH - restartButton.getWidth()) / 2);
        restartButton.setLayoutY((double) (2 * HEIGHT) / 3);

        getChildren().addAll(endText, restartButton);
    }
}
