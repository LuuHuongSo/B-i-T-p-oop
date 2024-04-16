import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class TankGame extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TANK_SIZE = 30;
    private static final int BULLET_SIZE = 5;

    private int playerTankX = WIDTH / 2;
    private int playerTankY = HEIGHT - TANK_SIZE - 50;
    private int aiTankX = WIDTH / 2;
    private int aiTankY = 50;

    private boolean isShooting = false;
    private int mybulletX;
    private int mybulletY;
    private List<Wall> walls = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Canvas win = new Canvas(400, 200); 

        StackPane root = new StackPane();
        root.getChildren().add(canvas);   

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        walls.add(new Wall(100, 100, 100, 20));
        walls.add(new Wall(100, 120, 20, 80));
        walls.add(new Wall(600, 100, 100, 20));
        walls.add(new Wall(680, 120, 20, 80));
        walls.add(new Wall(100, 400, 20, 80));
        walls.add(new Wall(100, 480, 100, 20));
        walls.add(new Wall(680, 400, 20, 80));
        walls.add(new Wall(600, 480, 100, 20));
        walls.add(new Wall(290, 90, 20, 60));
        walls.add(new Wall(490, 90, 20, 60));
        walls.add(new Wall(310, 130, 180, 20));
        walls.add(new Wall(290, 450, 20, 60));
        walls.add(new Wall(490, 450, 20, 60));
        walls.add(new Wall(310, 450, 180, 20));

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.A) {
                if(!biChanTrai()) {
                    movePlayerTank(-10);
                }
                //movePlayerTank(-10);
            } else if (event.getCode() == KeyCode.D) {
                if(!biChanPhai()) {
                    movePlayerTank(10);
                }
                //movePlayerTank(10);
            } else if (event.getCode() == KeyCode.W) {
                if(!biChanTren()){
                    movePlayerTankUpDown(10);
                }
                //movePlayerTankUpDown(10);
            } else if (event.getCode() == KeyCode.S) {
                if(!biChanDuoi()) {
                    movePlayerTankUpDown(-10);
                }
                //movePlayerTankUpDown(-10);
            } else if (event.getCode() == KeyCode.J) {
                shoot();
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.J) {
                isShooting = false;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tank");
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(gc);
                if (isShooting) {
                    moveBullet();
                }
            }
        }.start();
    }

    private void movePlayerTank(int dx) {
        int newX = playerTankX + dx;
        if (newX >= 0 && newX <= WIDTH - TANK_SIZE) {
            playerTankX = newX;
        }
    }

    private void movePlayerTankUpDown(int dy) {
        int newY = playerTankY - dy;
        if(newY >= 0 && newY <= HEIGHT - TANK_SIZE) {
            playerTankY = newY;
        } 
    }

    private void shoot() {
        if (!isShooting) {
            mybulletX = playerTankX + TANK_SIZE / 2 - BULLET_SIZE / 2;
            mybulletY = playerTankY;
            isShooting = true;
        }
    }

    private void moveBullet() {
        mybulletY -= 10;
        if (mybulletY < 0) {
            isShooting = false;
        }
    }

    private boolean biChanTren() {
        if(playerTankY == aiTankY + TANK_SIZE && playerTankX + TANK_SIZE > aiTankX && playerTankX < aiTankX + TANK_SIZE) return true;
        else return false;
    }
    private boolean biChanDuoi() {
        if(playerTankY + TANK_SIZE == aiTankY && playerTankX + TANK_SIZE > aiTankX && playerTankX < aiTankX + TANK_SIZE) return true;
        else return false;
    }
    private boolean biChanTrai() {
        if(playerTankX == aiTankX + TANK_SIZE && playerTankY + TANK_SIZE > aiTankY && playerTankY < aiTankY + TANK_SIZE) return true;
        else return false;
    }
    private boolean biChanPhai() {
        if(playerTankX + TANK_SIZE == aiTankX && playerTankY + TANK_SIZE > aiTankY && playerTankY < aiTankY + TANK_SIZE) return true;
        else return false;
    }


    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Vẽ tường
        for (Wall wall : walls) {
            wall.drawWall(gc);
        }

        // Ve tank nguoi choi
        gc.setFill(Color.BLUE);
        gc.fillRect(playerTankX, playerTankY, TANK_SIZE, TANK_SIZE);

        // Ve tank may
        gc.setFill(Color.RED);
        gc.fillRect(aiTankX, aiTankY, TANK_SIZE, TANK_SIZE);

        // Dan
        if (isShooting) {
            gc.setFill(Color.BLACK);
            gc.fillRect(mybulletX, mybulletY, BULLET_SIZE, BULLET_SIZE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
