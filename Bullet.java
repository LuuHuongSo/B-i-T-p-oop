import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private double x;
    private double y;
    private double tamX;
    private double tamY;
    private final double bulletsize = 5.0;
    //private double angle;

    public Bullet(double X, double Y) {
        this.x = X;
        this.y = Y;
        //this.angle = 0;
        this.tamX = this.x + this.bulletsize / 2;
        this.tamY = this.y + this.bulletsize / 2;
    } 
    public void draw(GraphicsContext gc, double angle) {
        gc.save();
        gc.translate(tamX, tamY);
        gc.rotate(Math.toDegrees(angle));
        gc.translate(-tamX, -tamY);

        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, bulletsize, bulletsize);

        gc.restore();
    }

    public void moveBullet(double angle) {
        x += 8 * Math.sin(angle);
        y -= 8 * Math.cos(angle);
        updateCenter();
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void updateCenter() {
        this.tamX = x + bulletsize / 2;
        this.tamY = y + bulletsize / 2;
    }
}
