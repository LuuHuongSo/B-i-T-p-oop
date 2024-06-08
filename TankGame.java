import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;



public class TankGame extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    private PlayerTank playerTank1;
    private PlayerTank playerTank2;
    //private AiTank aiTank;


    private Map gameMap;

    private boolean moveLeft1 = false;
    private boolean moveRight1 = false;
    private boolean moveForward1 = false;
    private boolean moveBackward1 = false;

    private boolean moveLeft2 = false;
    private boolean moveRight2 = false;
    private boolean moveForward2 = false;
    private boolean moveBackward2 = false;

    @Override
    public void start(Stage primaryStage) {

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        gameMap = new Map();
        playerTank1 = new PlayerTank(140, HEIGHT / 2 , Math.PI / 2, Color.DARKGREEN, 9);
        playerTank2 = new PlayerTank(640, HEIGHT / 2, Math.PI * 3 / 2 , Color.RED, 9);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    moveLeft1 = true;
                    break;
                case D:
                    moveRight1 = true;
                    break;
                case W:
                    moveForward1 = true;
                    break;
                case S:
                    moveBackward1 = true;
                    break;
                case J:
                    playerTank1.shoot();
                    break;
                case F:
                    playerTank1.useUltimate();
                    break;
                case LEFT:
                    moveLeft2 = true;
                    break;
                case RIGHT:
                    moveRight2 = true;
                    break;
                case UP:
                    moveForward2 = true;
                    break;
                case DOWN:
                    moveBackward2 = true;
                    break;
                case SPACE:
                    playerTank2.shoot();
                    break;
                case N:
                    playerTank2.useUltimate();
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A:
                    moveLeft1 = false;
                    break;
                case D:
                    moveRight1 = false;
                    break;
                case W:
                    moveForward1 = false;
                    break;
                case S:
                    moveBackward1 = false;
                    break;
                case J:
                    playerTank1.isShooting = true;
                    break;
                case LEFT:
                    moveLeft2 = false;
                    break;
                case RIGHT:
                    moveRight2 = false;
                    break;
                case UP:
                    moveForward2 = false;
                    break;
                case DOWN:
                    moveBackward2 = false;
                    break;
                case SPACE:
                    playerTank2.isShooting = true;
                    break;
                default:
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tank Game");
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (moveLeft1) playerTank1.rotateLeft();
                if (moveRight1) playerTank1.rotateRight();
                if (moveForward1) playerTank1.moveForWard(gameMap, playerTank2, WIDTH, HEIGHT);
                if (moveBackward1) playerTank1.moveBackward(gameMap, playerTank2, WIDTH, HEIGHT);

                if (moveLeft2) playerTank2.rotateLeft();
                if (moveRight2) playerTank2.rotateRight();
                if (moveForward2) playerTank2.moveForWard(gameMap, playerTank1, WIDTH, HEIGHT);
                if (moveBackward2) playerTank2.moveBackward(gameMap, playerTank1, WIDTH, HEIGHT);


                draw(gc);
                long currentTime = System.currentTimeMillis();

                if (playerTank1.isShooting) {
                    playerTank1.moveBullet(gameMap);
                    playerTank1.update(currentTime);
                }
                if (playerTank2.isShooting) {
                    playerTank2.moveBullet(gameMap);
                    playerTank2.update(currentTime);
                }
                if (playerTank1.isShooting && playerTank1.bullet.isHit(playerTank2)) {
                    playerTank2.reduceHp(playerTank1.bullet.getDamage());
                    playerTank1.isShooting = false;
                }
                if (playerTank2.isDestroyed()) {
                    playerTank2.drawExplosion(gc, currentTime);
                    if(!playerTank2.getIsExploding()) {
                        stop();
                        gameWin(primaryStage, "Player 1");
                    }
                }
                if (playerTank2.isShooting && playerTank2.bullet.isHit(playerTank1)) {
                    playerTank1.reduceHp(playerTank2.bullet.getDamage());
                    playerTank2.isShooting = false;
                }
                if (playerTank1.isDestroyed()) {
                    playerTank1.drawExplosion(gc, currentTime);
                    if(!playerTank1.getIsExploding()) {
                        stop();
                        gameWin(primaryStage, "Player 2");
                    }
                }
                //aiTank.move(playerTank.getX(), playerTank.getY(), gameMap);
                //aiTank.moveBullet();
            }
        }.start();
    }

    private void gameWin(Stage primaryStage, String player) {
        Label winLabel = new Label(player + " wins!");
        winLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        winLabel.setAlignment(Pos.CENTER);

        Image winnerImage = new Image("file:winner.png");
        ImageView imageView = new ImageView(winnerImage);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

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
        root.getChildren().addAll(winLabel, imageView, buttonBox);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Win Game");
        primaryStage.show();
    }



    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Vẽ bản đồ
        gameMap.draw(gc);

        // playerTank
        if(!playerTank1.isDestroyed()) {
            playerTank1.draw(gc);
        }
        if(!playerTank2.isDestroyed()) {
            playerTank2.draw(gc);
        }

        // Vẽ xe tăng máy
        //aiTank.draw(gc);

        // Vẽ đạn
        if (playerTank1.isShooting && !playerTank1.isDestroyed()) {
            playerTank1.bullet.draw(gc, playerTank1.getAngle());
        }
        if (playerTank2.isShooting && !playerTank2.isDestroyed()) {
            playerTank2.bullet.draw(gc, playerTank2.getAngle());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
