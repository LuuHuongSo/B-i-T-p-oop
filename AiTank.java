import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AiTank extends Tank {
    private double x;
    private double y;
    private double angle;
    private double tamX;
    private double tamY;
    public static final double width = 20;
    public static final double height = 30;
    private static final double SPEED = 1.5;
    private boolean isShooting = false;
    private double bulletX;
    private double bulletY;
    private double playerX;
    private double playerY;
    private static final double BULLET_SIZE = 5;

    public AiTank(double x, double y) {
        this.x = x;
        this.y = y;
        this.tamX = x + width / 2;
        this.tamY = y + height / 2;
        this.angle = 180;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(tamX, tamY);
        gc.rotate(Math.toDegrees(angle));
        gc.translate(-tamX, -tamY);

        gc.setFill(Color.RED);
        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);

        // Add some detail to the body
        gc.setFill(Color.RED);
        gc.fillRect(x + 2, y + 2, width - 4, height - 4);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x + 2, y + 2, width - 4, height - 4);

        // Draw the turret
        double turretWidth = 12;
        double turretHeight = 12;
        gc.setFill(Color.DARKOLIVEGREEN);
        gc.fillRect(x + (width - turretWidth) / 2, y + (height - turretHeight) / 3, turretWidth, turretHeight);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x + (width - turretWidth) / 2, y + (height - turretHeight) / 3, turretWidth, turretHeight);

        // Draw the barrel
        double barrelWidth = 5;
        double barrelHeight = 15;
        gc.setFill(Color.GRAY);
        gc.fillRect(x + (width - barrelWidth) / 2, y - barrelHeight, barrelWidth, barrelHeight);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x + (width - barrelWidth) / 2, y - barrelHeight, barrelWidth, barrelHeight);

        gc.restore();

        if (isShooting) {
            gc.setFill(Color.RED);
            gc.fillRect(bulletX, bulletY, BULLET_SIZE, BULLET_SIZE);
        }
    }

    public void move(double playerX, double playerY, Map map) {
        this.playerX = playerX;
        this.playerY = playerY;

        double dx = playerX - x;
        double dy = playerY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 200) {
            double targetAngle = Math.toDegrees(Math.atan2(dy, dx));
            if (targetAngle < 0) targetAngle += 360;

            if (Math.abs(targetAngle - angle) > 5) {
                if ((targetAngle - angle + 360) % 360 < 180) {
                    angle += 2;
                } else {
                    angle -= 2;
                }
            } else {
                x += SPEED * Math.sin(Math.toRadians(angle));
                y += SPEED * Math.cos(Math.toRadians(angle));
            }

            shoot();
        }
    }

    private void shoot() {
        if (!isShooting) {
            bulletX = x + width / 2 - BULLET_SIZE / 2;
            bulletY = y;
            isShooting = true;
        }
    }

    public void moveBullet() {
        if (isShooting) {
            bulletX += 4 * Math.cos(Math.toRadians(angle));
            bulletY += 4 * Math.sin(Math.toRadians(angle));

            if (bulletX < 0 || bulletX > 800 || bulletY < 0 || bulletY > 600) {
                isShooting = false;
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }
}
