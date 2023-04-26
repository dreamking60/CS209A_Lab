package zihan.assignment.practice9;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SpaceGame extends Application {

    private Pane root = new Pane();
    private Label scoreLabel = new Label("Score: 0");
    private Label gameOverLabel = new Label("GAME OVER");
    private int score = 0;
    private boolean gameOver = false;
    private boolean playerCanShoot = true;

    private Sprite player = new Sprite(300, 750, 40, 40, "player", Color.BLUE);

    private Parent createContent() {
        root.setPrefSize(600, 800);

        root.getChildren().add(player);
        root.getChildren().add(scoreLabel);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        createEnemies();

        return root;
    }

    private void createEnemies() {
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i*100, 150, 30, 30, "enemy", Color.RED);

            root.getChildren().add(s);
            sprites().add(s);
        }
    }

    private List<Sprite> sprites() {
        return root.getChildren().stream().filter(n -> n instanceof Sprite).map(n -> (Sprite)n).collect(Collectors.toList());
    }

    private void update() {
        if (gameOver) {
            return;
        }

        sprites().forEach(s -> {
            switch (s.type) {
                case "enemybullet":
                    // enemy's bullet moves down
                    s.moveDown();

                    // enemy's bullet hits the player
                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true; // player is dead
                        s.dead = true; // bullet is dead
                        gameOver();
                    }
                    break;

                case "playerbullet":
                    // player's bullet moves up
                    s.moveUp();

                    // player's bullet hits each enemy
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(e -> {
                        if (s.getBoundsInParent().intersects(e.getBoundsInParent())) {
                            e.dead = true; // enemy is dead
                            s.dead = true; // bullet is dead
                            increaseScore();
                        }
                    });
                    break;

                case "enemy":
                    // enemies shoot with random intervals
                    if (System.currentTimeMillis() - s.lastShotTime >= s.timeInterval) {
                        shoot(s);
                        s.lastShotTime = System.currentTimeMillis();
                        s.timeInterval = new Random().nextInt(3000) + 2000; // random interval between 2 and 5 seconds
                    }
                    break;
            }
        });

        // remove dead sprites from the screen
        root.getChildren().removeIf(n -> {
            if(n instanceof Sprite) {
                Sprite s = (Sprite) n;
                return s.dead;
            }
            return false;
        });
    }

    private void increaseScore() {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    private void shoot(Sprite who) {
        if (who.dead) {
            return;
        }
        // a rectangle with width 5, which looks like a bullet
        Sprite s = new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), 5, 20, who.type + "bullet", Color.BLACK);

        root.getChildren().add(s);
    }

    private void gameOver() {
        gameOver = true;
        root.getChildren().add(gameOverLabel);
        gameOverLabel.setLayoutX(250);
        gameOverLabel.setLayoutY(400);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(e -> {
            if (gameOver) {
                return;
            }

            switch (e.getCode()) {
                case LEFT:
                    player.moveLeft();
                    break;
                case RIGHT:
                    player.moveRight();
                    break;
                case UP:
                    player.moveUp();
                    break;
                case DOWN:
                    player.moveDown();
                    break;
                case SPACE:
                    if (playerCanShoot) {
                        shoot(player);
                        playerCanShoot = false;
                        // set a delay before the player can shoot again
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        playerCanShoot = true;
                                    }
                                },
                                500 // delay time in milliseconds
                        );
                        // 500 millisecond delay ensures the player can't shoot too frequently
                    }
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private static class Sprite extends Rectangle {
        boolean dead = false;
        final String type;
        long lastShotTime = System.currentTimeMillis();
        int timeInterval = 5000; // 5 seconds (in milliseconds)

        Sprite(int x, int y, int w, int h, String type, Color color) {
            super(w, h, color);

            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }

        void moveLeft() {
            if (getTranslateX() >= 5) {
                setTranslateX(getTranslateX() - 5);
            }
        }

        void moveRight() {
            if (getTranslateX() + getWidth() + 5 <= 600) {
                setTranslateX(getTranslateX() + 5);
            }
        }

        void moveUp() {
            if (getTranslateY() >= 5) {
                setTranslateY(getTranslateY() - 5);
            }
        }

        void moveDown() {
            if (getTranslateY() + getHeight() + 5 <= 800) {
                setTranslateY(getTranslateY() + 5);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}