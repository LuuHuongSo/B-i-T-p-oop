import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Game extends Application {
    private PlayerTank playerTank;
    private AiMap aiMap;
    private ArrayList<AiTank> aiTanks;

    private int score = 0;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final double SPAWN_INTERVAL = 5; // seconds
    private static final int MAX_AITANKS = 3;
    private Random random = new Random();

    private boolean moveLeft = false; 
    private boolean moveRight = false;
    private boolean moveForward = false;
    private boolean moveBackward = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        playerTank = new PlayerTank(390, HEIGHT - 100, 0, Color.DARKGREEN, 20);
        aiMap = new AiMap();
        aiTanks = new ArrayList<>();

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    moveLeft = true;
                    break;
                case D:
                    moveRight = true;
                    break;
                case W:
                    moveForward = true;
                    break;
                case S:
                    moveBackward = true;
                    break;
                case J:
                    playerTank.shoot();
                    break;
                case F:
                    playerTank.useUltimate();
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A:
                    moveLeft = false;
                    break;
                case D:
                    moveRight = false;
                    break;
                case W:
                    moveForward = false;
                    break;
                case S:
                    moveBackward = false;
                    break;
                case J:
                    playerTank.isShooting = true;
                    break;
                default:
                    break;
            }
        });
        Timeline spawnTimeline = new Timeline(new KeyFrame(Duration.seconds(SPAWN_INTERVAL), event -> {
            spawnAiTank();
        }));
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);

        spawnTimeline.play();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Tank Game");
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (moveLeft) playerTank.rotateLeft();
                if (moveRight) playerTank.rotateRight();
                if (moveForward) playerTank.moveForWardAi(300,400);
                if (moveBackward) playerTank.moveBackwardAi(300, 400);


                draw(gc);
                long currentTime = System.currentTimeMillis();

                if (playerTank.isShooting) {
                    playerTank.moveBullet();
                    playerTank.update(currentTime);
                }
                for(AiTank aiTank : aiTanks) {
                    if (playerTank.isShooting && playerTank.bullet.isHit(aiTank)) {
                        aiTank.reduceHp(playerTank.bullet.getDamage());
                        playerTank.isShooting = false;
                    }
                    if(aiTank.isShooting && aiTank.bullet.isHit(playerTank)) {
                        playerTank.reduceHp(2);
                        aiTank.isShooting = false;
                    }
                }
                for(AiTank aiTank : aiTanks) {
                    if (aiTank.isDestroyed()) {
                        score ++;
                        aiTank.drawExplosion(gc, currentTime);
                    }
                }
                for(AiTank aiTank : aiTanks) {
                    if(!aiTank.isDestroyed()) {
                        aiTank.move(playerTank);
                    }
                }
                if(playerTank.isDestroyed()) {
                    playerTank.drawExplosion(gc, currentTime);
                    if(!playerTank.getIsExploding()) {
                        stop();
                        gameWin(primaryStage, score);
                    }
                }
            }
        }.start();
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        aiMap.draw(gc);
        playerTank.draw(gc);

        Iterator<AiTank> iterator = aiTanks.iterator();
        while (iterator.hasNext()) {
            AiTank aiTank = iterator.next();
            if (!aiTank.isDestroyed()) {
                aiTank.draw(gc);
            } else {
                // Nếu tank đã bị phá hủy, xóa khỏi ArrayList
                iterator.remove();
            }
        }

        if (playerTank.isShooting && !playerTank.isDestroyed()) {
            playerTank.bullet.draw(gc, playerTank.getAngle());
        }
        drawScore(gc);
    };

    private void spawnAiTank() {
        if (aiTanks.size() < MAX_AITANKS) {
            double x = random.nextDouble() * WIDTH;
            AiTank aiTank = new AiTank(x, 0, Math.PI, 2);
            aiTanks.add(aiTank);
        }
    }

    private void drawScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 10, 20);
    }
    private void gameWin(Stage primaryStage, int score) {
        Image backgroundImage = new Image("file:C:/Users/Admin/Downloads/TankGame.jpg");

        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);
        backgroundImageView.setPreserveRatio(true);
        Label winLabel = new Label("Số tank bạn đã bắn hạ: " + score);
        winLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        winLabel.setAlignment(Pos.CENTER);

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(event -> {
            this.start(primaryStage);
        });

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(event -> {
            GameUI gameUI = new GameUI();
            gameUI.start(primaryStage);
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            primaryStage.close();
        });

        // Arrange buttons in an HBox
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(playAgainButton, mainMenuButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(winLabel, buttonBox, backgroundImageView);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Win Game");
        primaryStage.show();
    }

}

