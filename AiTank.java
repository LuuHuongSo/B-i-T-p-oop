import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AiTank extends Tank {
    private double tamX;
    private double tamY;
    public static final double width = 20;
    public static final double height = 30;
    private int hp = 20;

    public AiTank(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.tamX = x + width / 2;
        this.tamY = y + height / 2;
        this.angle = angle;
        this.color = Color.DARKBLUE;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(tamX, tamY);
        gc.rotate(Math.toDegrees(angle));
        gc.translate(-tamX, -tamY);

        gc.setFill(color);
        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);

        // Add some detail to the body
        gc.setFill(color);
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

        //draw hp
        gc.setFill(Color.RED);
        gc.fillRect(x - 15, y + 33, 45, 5);
        gc.setFill(Color.GREEN);
        gc.fillRect(x - 15, y + 33, this.getHp() * 5, 5);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x - 15, y + 33, 45, 5);


        gc.restore();

        if (isShooting) {
            double bulletangle = this.angle;
            bullet.draw(gc, bulletangle);
        }
    }

    public void move(PlayerTank p1, Map map) {
        double angleToP1 = Math.atan2(p1.getY() - this.y, p1.getX() - this.x);

        boolean canSeeP1 = map.isLineOfSightClear(this.getX(), this.getY(), p1.getX(), p1.getY());

        if (canSeeP1) {
            double rotationToP1 = Math.abs(this.angle - angleToP1);
            this.angle = angleToP1;
            this.shoot();
        }
    }

    public double getBarrelEndX() {
        return x + width / 2 + (height / 2 * Math.sin(angle));
    }

    public double getBarrelEndY() {
        return y + height / 2 - (height / 2 * Math.cos(angle));
    }

    private void shoot() {
        if(!isShooting) {
            double bulletX = this.getBarrelEndX();
            double bulletY = this.getBarrelEndY();
            double bulletAngle = this.angle;
            bullet = new Bullet(bulletX, bulletY, bulletAngle, 1.5, 5.0, Color.BLACK);
            isShooting = true;
        }
    }

    public void moveBullet(Map gameMap) {
        if (isShooting) {
            bullet.moveBullet();
            if(bullet.getDistanceTraveled() >= 250 || gameMap.isBulletCollision(this.bullet)) {
                this.isShooting = false;
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
