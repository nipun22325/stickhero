package com.example.stickhero;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class MainGameplay extends Application implements Serializable{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private double lineHeight = 0.0;
    private boolean isMousePressed = false;
    private boolean isAltPressed = false;
    private double stickLength = 0.0;
    private boolean running=true;
    private double lastPlatformMiddle;
    public static double translateX = 200;
    public static int cherriesCollected = 0;
    private boolean rotateFinished = false;
    private int stickNum=0;
    private boolean gameover = false;
    private int bestScore = 0;
    protected static int score=0;

    private StartScreen startScreen;
    private List<Stick> sticks;
    private List<Cherry> cherries;
    private Pane root;
    private Character character;
    private List<Platform> platforms;
    private Canvas canvas;
    private GraphicsContext gc;

    public void saveGameState() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("gameState.txt"))) {
            writer.println(score);
            writer.println(bestScore);
            writer.println(cherriesCollected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreGameState() {
        try (BufferedReader reader = new BufferedReader(new FileReader("gameState.txt"))) {
            score = Integer.parseInt(reader.readLine());
            bestScore = Integer.parseInt(reader.readLine());
            cherriesCollected = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stick Hero Game");
        root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);




        Image backgroundImage = new Image("file:background.jpg");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(800, 600, false, false, false, false)
        );
        root.setBackground(new Background(background));

        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        startScreen = new StartScreen(() -> startGame());
        root.getChildren().add(startScreen);

        sticks = new ArrayList<>();
        primaryStage.show();
    }

    private void extendStick() {
        lineHeight += 5;




        gc.setFill(Color.BROWN);
        gc.fillRect(character.getX() - 5, HEIGHT - 100 - lineHeight, 10, lineHeight);
    }

    private void saveStick() {
        stickNum+=1;
        Stick newStick = new Stick(character.getX(), lineHeight, Color.BROWN,stickNum);
        sticks.add(newStick);
        stickLength=lineHeight;
        lineHeight = 0.0;
    }

    private void startGame() {
        SoundPlayer.playSound("./backgroundMusic.wav", 0.1f);
        root.getChildren().remove(startScreen);
        Generator objGen = new Generator();
        platforms = objGen.spawnRandomPlatforms(100);
        cherries=objGen.spawnRandomCherries();
        Platform firstPlatform = platforms.get(0);
        character = new Character(firstPlatform.getX() + firstPlatform.getWidth() - 20, firstPlatform.getY());

        /*Observer is a behavioral design pattern which specifies communication between objects: observable and observers.
        An observable is an object which notifies observers about the changes in its state.
        The AnimationTimer usage in the startGame() method follows the observer pattern implicitly.
        Here the AnimationTimer is the observable and the startGame() method is the observer
        The game loop updates the game state continuously.*/
        new AnimationTimer() {
            @Override
            public void handle(long instant) {
                if(running) {
                    gc.clearRect(0, 0, WIDTH, HEIGHT);
                    gc.setFill(Color.BLACK);
                    gc.setFont(Font.font(20));
                    gc.fillText("Score: " + score, 10, 30);
                    gc.setFill(Color.BLACK);
                    gc.setFont(Font.font(20));
                    gc.fillText("Cherries: " + cherriesCollected, WIDTH - 150, 30);

                    for (Platform platform : platforms) {
                        platform.make(gc);
                    }

                    try {
                        for (Cherry cherry : new ArrayList<>(cherries)) {
                            cherry.make(gc);
                            if (character.isFlipped && (int) character.getX() == (int) cherry.getX()) {
                                objGen.collectCherry(cherry);
                            }
                        }
                    } catch (ConcurrentModificationException ignored) {}
                    
                    if (gameover) {
                        if(cherriesCollected>=2){  // revive automatically if a character dies when more than 2 cherries have been already collected.
                            cherriesCollected-=2;
                            gameover=false;
                            character.setX(lastPlatformMiddle);
                            sticks.clear();
                        }
                        else {
                            displayFinalScreen();
                            stop();
                        }
                    }
                    
                    for (Stick stick : sticks) {
                        rotateFinished = false;
                        stick.update();
                        stick.make(gc);
                        if (stick.getRotateAngle() == 90 && stick.getStickNum() == stickNum) {
                            rotateFinished = true;
                        }

                        boolean collision = hasCollided();
                        if (character.isFlipped && collision) {
                            gameover = true;
                        }

                        if (rotateFinished && stick.getStickNum()== stickNum) {
                            moveChar(stick);
                        }
                    }

                    if (isAltPressed) {
                        character.flip();
                        isAltPressed = false;
                    }

                    if (isMousePressed) {
                        extendStick();
                    }
                    character.make(gc);
                }
            }

            private void moveChar(Stick Stick) {
                double charX = character.getX();
                if (charX < (Stick.getTopX() + stickLength)) {
                    character.setX(charX + 1);
                }
                else if(charX == (Stick.getTopX() + stickLength)){
                    boolean isOnPlatform = false;
                    for (int[] platformRange : Generator.endings) {
                        if (charX >= platformRange[0] && charX <= platformRange[1]) {
                            isOnPlatform = true;
                            score++;
                            translateX = platformRange[1] - WIDTH / 2;
                            objGen.updateEntities(character,sticks);
                            character.setX(character.getX() + 1);
                            break;
                        }
                    }
                    if (!isOnPlatform) {
                        for (int[] platformRange : Generator.endings) {
                            if (charX > platformRange[1]) {
                                lastPlatformMiddle= (double) (platformRange[1] + platformRange[0]) /2;
                            }
                            else{
                                break;
                            }
                        }
                        gameover = true;

                    }
                }
            }



            public boolean hasCollided(){
                boolean ischarPlatformCollide = false;
                for(int[] x:Generator.endings){
                    if (character.getX() > x[0] && character.getX() < x[1]) {
                        ischarPlatformCollide = true;
                        break;
                    }
                }
                return ischarPlatformCollide;
            }


        }.start();

        Scene scene = canvas.getScene();
        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                isMousePressed = false;
                saveStick();
            }
        });
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                isMousePressed = true;
            }
        });

        Scene scene2 = canvas.getScene();
        scene2.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ALT) {
                isAltPressed=true;
            } else if (event.getCode() == KeyCode.S) {
                saveGameState();
            } else if (event.getCode()==KeyCode.R) {
                restoreGameState();
            }
        });
        scene2.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ALT) {
                isAltPressed=false;
            }
        });
    }

    private void displayFinalScreen() {
        if (score > bestScore) {
            bestScore = score;
        }
        FinalScreen finalScreen = new FinalScreen(score,cherriesCollected,bestScore, this::restartGame);
        root.getChildren().add(finalScreen);
    }






    public void restartGame() {
        gameover = false;score = 0;
        sticks.clear();platforms.clear();Generator.endings.clear();
        translateX = 200;
        root.getChildren().removeIf(node -> node instanceof FinalScreen);
        startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
