import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TankGame extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    private PlayerTank playerTank;
    private AiTank aiTank;

    private boolean isShooting = false;
    private Bullet mybullet;

    private Map gameMap;

    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveForward = false;
    private boolean moveBackward = false;

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
        playerTank = new PlayerTank(WIDTH / 2, HEIGHT - 80);
        aiTank = new AiTank(WIDTH / 2, 50);

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
                    shoot();
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
                    isShooting = true;
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
                if (moveLeft) playerTank.rotateLeft();
                if (moveRight) playerTank.rotateRight();
                if (moveForward && !isCollidingWithAi()) playerTank.moveForWard(gameMap);
                if (moveBackward && !isCollidingWithAi()) playerTank.moveBackward(gameMap);

                draw(gc);
                if (isShooting) {
                    moveBullet();
                }
                aiTank.move(playerTank.getX(), playerTank.getY(), gameMap);
                aiTank.moveBullet();
            }
        }.start();
    }

    private void shoot() {
        if (!isShooting) {
            double bulletX = playerTank.getBarrelEndX();
            double bulletY = playerTank.getBarrelEndY();
            mybullet = new Bullet(bulletX, bulletY);
            isShooting = true;
        }
    }

    private void moveBullet() {
        mybullet.moveBullet(playerTank.getAngle());
        if (gameMap.isBulletCollision(mybullet) || mybullet.getX() < 0 || mybullet.getX() > WIDTH || mybullet.getY() < 0 || mybullet.getY() > HEIGHT) {
            isShooting = false;
        }
    }

    private boolean isCollidingWithAi() {
        if (playerTank.getBounds().intersects(aiTank.getBounds())) {
            return true;
        }
        return false;
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Vẽ bản đồ
        gameMap.draw(gc);

        // Vẽ xe tăng người chơi
        playerTank.draw(gc);

        // Vẽ xe tăng máy
        aiTank.draw(gc);

        // Vẽ đạn
        if (isShooting) {
            mybullet.draw(gc, playerTank.getAngle());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
