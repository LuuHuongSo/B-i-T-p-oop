import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class AiTank extends Tank {
    private double tamX;
    private double tamY;
    public static final double width = 20;
    public static final double height = 30;
    private boolean isMoving = true;
    private double angleToP1;
    private final double ROTATION_SPEED = 0.05;
    private final double SPEED = 1.5;
    public boolean isExploding = false;

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

        // Apply transformations for rotation around the tank's center
        gc.translate(tamX, tamY);
        gc.rotate(Math.toDegrees(angle));
        gc.translate(-tamX, -tamY);

        // Draw the tank's body with detailed elements
        gc.setFill(color);
        gc.fillRoundRect(x, y, width, height, 10, 10);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(x, y, width, height, 10, 10);

        // Add inner detail to the body
        gc.setFill(color);
        gc.fillRoundRect(x + 2, y + 2, width - 4, height - 4, 8, 8);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(x + 2, y + 2, width - 4, height - 4, 8, 8);

        // Draw bolts on the tank body
        gc.setFill(Color.GRAY);
        double[] boltOffsetsX = { 4, width - 8, 4, width - 8 };
        double[] boltOffsetsY = { 4, 4, height - 8, height - 8 };
        for (int i = 0; i < boltOffsetsX.length; i++) {
            gc.fillOval(x + boltOffsetsX[i], y + boltOffsetsY[i], 4, 4);
        }

        // Draw the turret with rounded edges
        double turretWidth = 14;
        double turretHeight = 14;
        gc.setFill(Color.DARKSLATEGRAY);
        gc.fillRoundRect(x + (width - turretWidth) / 2, y + (height - turretHeight) / 3, turretWidth, turretHeight, 5, 5);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(x + (width - turretWidth) / 2, y + (height - turretHeight) / 3, turretWidth, turretHeight, 5, 5);

        // Draw turret hatch
        gc.setFill(Color.LIGHTGRAY);
        gc.fillOval(x + (width - 8) / 2, y + (height - turretHeight) / 3 + 3, 8, 8);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x + (width - 8) / 2, y + (height - turretHeight) / 3 + 3, 8, 8);

        // Draw the barrel with a rounded end
        double barrelWidth = 5;
        double barrelHeight = 20;
        gc.setFill(Color.DARKSLATEGRAY);
        gc.fillRect(x + (width - barrelWidth) / 2, y - barrelHeight, barrelWidth, barrelHeight);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x + (width - barrelWidth) / 2, y - barrelHeight, barrelWidth, barrelHeight);

        // Draw barrel detail
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x + (width - barrelWidth) / 2, y - barrelHeight, barrelWidth, 5);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x + (width - barrelWidth) / 2, y - barrelHeight, barrelWidth, 5);

        // Draw tank tracks with more details
        double trackWidth = 3;
        double trackHeight = 6;
        gc.setFill(Color.DARKGRAY);
        for (int i = 0; i < width; i += 6) {
            gc.fillRect(x + i, y + height, trackWidth, trackHeight);
            gc.fillRect(x + i, y - trackHeight, trackWidth, trackHeight);
        }

        // Draw track treads
        gc.setFill(Color.BLACK);
        for (int i = 0; i < width; i += 2) {
            gc.fillRect(x + i, y + height, 1, trackHeight);
            gc.fillRect(x + i, y - trackHeight, 1, trackHeight);
        }

        // Draw the health bar
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

    public void move(PlayerTank p1) {
        double khoangcachX = (p1.getX() - this.getX()) * (p1.getX() - this.getX());
        double khoangcachY = (p1.getY() - this.getY()) * (p1.getY() - this.getY());
        double khoangcach = Math.sqrt(khoangcachX + khoangcachY);
        if(this.x > p1.getX()) {
            this.angleToP1 = Math.atan((this.x - p1.getX()) / (p1.getY() - this.y));
            if(this.angle < angleToP1+Math.PI) {
                rotateRight();
            }
        }else {
            this.angleToP1 = Math.atan((p1.x - this.getX()) / (p1.getY() - this.y));
            if(this.angle > Math.PI - angleToP1) {
                rotateLeft();
            }
        }
        if (isShooting) {
            moveBullet();
        }
        if(isMoving) {
            this.moveForWard();
        }
        if(khoangcach <= 280) {
            shoot();
            this.isMoving = false;
        }
        shoot();
    }

    public void moveForWard() {
        double newX = x + SPEED * Math.sin(angle);
        double newY = y - SPEED * Math.cos(angle);
        x = newX;
        y = newY;
        updateCenter();
    }

    private void updateCenter() {
        this.tamX = x + width / 2;
        this.tamY = y + height / 2;
    }
    public void rotateLeft() {
        angle -= ROTATION_SPEED;
    }

    public void rotateRight() {
        angle += ROTATION_SPEED;
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
            bullet = new Bullet(bulletX, bulletY, bulletAngle, 1, 5.0, Color.BLACK);
            isShooting = true;
        }
    }

    public void moveBullet() {
        if (isShooting) {
            bullet.moveBullet();
            if(bullet.getDistanceTraveled() >= 300) {
                this.isShooting = false;
            }
        }
    }
    public void drawExplosion(GraphicsContext gc, long currentTime) {
        if (this.isDestroyed() && !this.isExploding) {
            explosionStartTime = System.currentTimeMillis();
            isExploding = true;
        }

        long elapsed = currentTime - explosionStartTime;
        if (elapsed > EXPLOSION_DURATION) {
            isExploding = false;
            return;
        }

        int numFrames = 30;
        long frameDuration = EXPLOSION_DURATION / numFrames;
        long timeSinceStart = currentTime - explosionStartTime;
        int currentFrame = (int) (timeSinceStart / frameDuration);

        double explosionProgress = (double) timeSinceStart / EXPLOSION_DURATION;
        double maxExplosionSize = width * 4;
        double explosionSize = maxExplosionSize * explosionProgress;

        for (int i = 0; i < currentFrame; i++) {
            double frameProgress = (double) i / numFrames;
            double frameExplosionSize = maxExplosionSize * frameProgress;
            double frameAngleOffset = 360.0 / numFrames * i;

            gc.setFill(Color.ORANGE);
            gc.fillOval(x - frameExplosionSize / 2, y - frameExplosionSize / 2, frameExplosionSize, frameExplosionSize);

            gc.setFill(Color.RED);
            gc.fillArc(x - frameExplosionSize / 2, y - frameExplosionSize / 2, frameExplosionSize, frameExplosionSize, 45 + frameAngleOffset, 270, ArcType.ROUND);

            int numSparks = 20;
            double sparkLength = 20;
            for (int j = 0; j < numSparks; j++) {
                double sparkAngle = 2 * Math.PI * j / numSparks + frameAngleOffset;
                double sparkX = x + (frameExplosionSize / 2) * Math.cos(sparkAngle);
                double sparkY = y + (frameExplosionSize / 2) * Math.sin(sparkAngle);
                gc.setStroke(Color.YELLOW);
                gc.strokeLine(x, y, sparkX, sparkY);
            }
        }
    }

}
