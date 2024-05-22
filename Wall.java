import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall {
    private double toadoX;
    private double toadoY;
    private double dai;
    private double rong;


    public Wall(double x, double y, double dai, double rong) {
        this.toadoX = x;
        this.toadoY = y;
        this.dai = dai;
        this.rong = rong;
    }

    public Rectangle2D getBounds() {
            return new Rectangle2D(toadoX, toadoY, dai, rong);
        }


    public void drawWall(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(toadoX, toadoY, dai, rong);
    }

    public double getToaDoX() {
        return toadoX;
    }
    public double getToaDoY() {
        return toadoY;
    }
    public double getDai() {
        return dai;
    }
    public double getRong() {
        return rong;
    }
}