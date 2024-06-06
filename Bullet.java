import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class Bullet {
    private double x;
    private double y;
    private final double angle;
    private double distanceTraveled = 0.0;
    private double tamX;
    private double tamY;
    private final double damage;
    private final double bulletsize;
    private final Color color;

    public Bullet(double X, double Y, double angle, double damage, double size, Color color) {
        this.x = X;
        this.y = Y;
        this.angle = angle;
        this.damage = damage;
        this.bulletsize = size;
        this.color = color;
        this.tamX = this.x + this.bulletsize / 2;
        this.tamY = this.y + this.bulletsize / 2;
    }

    public void draw(GraphicsContext gc, double angle) {
        gc.save();
        gc.translate(tamX, tamY);
        gc.rotate(Math.toDegrees(angle));
        gc.translate(-tamX, -tamY);

        // Tạo gradient cho đạn
        RadialGradient gradient = new RadialGradient(0, 0, tamX, tamY, bulletsize / 2, false, null,
                new Stop(0, color),
                new Stop(1, Color.BLACK));

        gc.setFill(gradient);
        gc.fillRoundRect(x, y, bulletsize, bulletsize * 1.6, bulletsize / 2, bulletsize / 2);

        gc.setFill(Color.ORANGE);
        gc.fillOval(x - bulletsize * 0.2, y + bulletsize * 1.6, bulletsize * 1.4, bulletsize * 0.8); // Hiển thị lửa sau tên lửa

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

    public double getDamage() {
        return damage;
    }

    public void updateCenter() {
        this.tamX = x + bulletsize / 2;
        this.tamY = y + bulletsize / 2;
    }

    public boolean isHit(Tank tank) {
        Rectangle2D bulletBounds = new Rectangle2D(x, y, bulletsize, bulletsize * 1.6);
        return bulletBounds.intersects(tank.getBounds());
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }
}
