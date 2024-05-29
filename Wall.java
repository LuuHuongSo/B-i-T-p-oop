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
        gc.setFill(Color.SADDLEBROWN);
        gc.fillRect(toadoX, toadoY, dai, rong);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(toadoX, toadoY, dai, rong);

        gc.setStroke(Color.DARKRED);
        gc.setLineWidth(1);
        gc.strokeLine(toadoX + dai * 0.2, toadoY + rong * 0.3, toadoX + dai * 0.4, toadoY + rong * 0.5);
        gc.strokeLine(toadoX + dai * 0.6, toadoY + rong * 0.1, toadoX + dai * 0.8, toadoY + rong * 0.3);
        gc.strokeLine(toadoX + dai * 0.1, toadoY + rong * 0.7, toadoX + dai * 0.3, toadoY + rong * 0.9);
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
