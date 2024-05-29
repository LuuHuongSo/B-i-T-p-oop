import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private double x;
    private double y;
    private double angle;
    private double distanceTraveled = 0.0;
    private double tamX;
    private double tamY;
    private final double bulletsize = 5.0;

    public Bullet(double X, double Y, double angle) {
        this.x = X;
        this.y = Y;
        this.angle = angle;
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

    public void moveBullet() {
        double dx = 8 * Math.sin(angle);
        double dy = 8 * Math.cos(angle);
        x += dx;
        y -= dy;
        distanceTraveled += Math.sqrt(dx * dx + dy * dy);
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
    public boolean isHit(Tank tank) {
        Rectangle2D bulletBounds = new Rectangle2D(x, y, bulletsize, bulletsize);
        return bulletBounds.intersects(tank.getBounds());
    }
    public double getDistanceTraveled() {
        return distanceTraveled;
    }
}
