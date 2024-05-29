import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TankGame extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    private PlayerTank playerTank1;
    private PlayerTank playerTank2;
    //private AiTank aiTank;

    //private boolean isShooting = false;
    //private Bullet mybullet;

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

        Image backgroundImage = new Image("file:C:/Users/Admin/Downloads/Background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.setFitWidth(WIDTH);
        backgroundImageView.setFitHeight(HEIGHT);
        backgroundImageView.setPreserveRatio(false);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        gameMap = new Map();
        playerTank1 = new PlayerTank(WIDTH / 2, HEIGHT - 80, 0);
        playerTank2 = new PlayerTank(WIDTH / 2, 50, 180);

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
                if (playerTank1.isShooting) {
                    playerTank1.moveBullet(gameMap, WIDTH, HEIGHT);
                }
                if (playerTank2.isShooting) {
                    playerTank2.moveBullet(gameMap, WIDTH, HEIGHT);
                }
                if (playerTank1.isShooting && playerTank1.bullet.isHit(playerTank2)) {
                    playerTank2.reduceHp();
                    playerTank1.isShooting = false;
                    if (playerTank2.isDestroyed()) {
                        stop();
                        displayWinner("Player 1");
                    }
                }
                if (playerTank2.isShooting && playerTank2.bullet.isHit(playerTank1)) {
                    playerTank1.reduceHp();
                    playerTank2.isShooting = false;
                    if (playerTank1.isDestroyed()) {
                        stop();
                        displayWinner("Player 2");
                    }
                }
                //aiTank.move(playerTank.getX(), playerTank.getY(), gameMap);
                //aiTank.moveBullet();
            }
        }.start();
    }


    /*private boolean isCollidingWithO() {
        if (playerTank1.getBounds().intersects(playerTank2.getBounds())) {
            return true;
        }
        return false;
    }*/
    private void displayWinner(String winner) {
        System.out.println(winner + " wins!");
    }


    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Vẽ bản đồ
        gameMap.draw(gc);

        // Vẽ xe tăng người chơi
        playerTank1.draw(gc);
        playerTank2.draw(gc);

        // Vẽ xe tăng máy
        //aiTank.draw(gc);

        // Vẽ đạn
        if (playerTank1.isShooting) {
            playerTank1.bullet.draw(gc, playerTank1.getAngle());
        }
        if (playerTank2.isShooting) {
            playerTank2.bullet.draw(gc, playerTank2.getAngle());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
