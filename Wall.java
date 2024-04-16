import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Wall {
    private int toadoX;
    private int toadoY;
    private int dai;
    private int rong;


    public Wall(int x, int y, int dai, int rong) {
        this.toadoX = x;
        this.toadoY = y;
        this.dai = dai;
        this.rong = rong;
    }

    public void drawWall(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(toadoX, toadoY, dai, rong);
    }
}
