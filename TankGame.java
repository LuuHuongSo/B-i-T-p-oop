import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TankGame extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    //private static final double BULLET_SIZE = 5;

    private PlayerTank playerTank;
    private AiTank aiTank;

    private boolean isShooting = false;
    private Bullet mybullet;

    private Map gameMap;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        gameMap = new Map();
        playerTank = new PlayerTank(WIDTH / 2, HEIGHT - 80);
        aiTank = new AiTank(WIDTH / 2, 50);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    playerTank.rotateLeft();
                    break;
                case D:
                    playerTank.rotateRight();
                    break;
                case W:
                    if(!isCollidingWithAi()){
                        playerTank.moveForWard(gameMap);
                    }
                    //playerTank.moveForWard(gameMap);
                    break;
                case S:
                    if(!isCollidingWithAi()) {
                        playerTank.moveBackward(gameMap);
                    }
                    break;
                case J:
                    shoot();
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.J) {
                isShooting = true;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tank Game");
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
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
            double bulletX = playerTank.getX() + playerTank.height / 2 * Math.sin(playerTank.getAngle());
            double bulletY = playerTank.getY() + playerTank.height / 2 - (playerTank.height / 2 * Math.cos(playerTank.getAngle()));
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
