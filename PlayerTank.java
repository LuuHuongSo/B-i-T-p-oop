import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class PlayerTank extends Tank {
    private double tamX;
    private double tamY;
    public double width = 20;
    public double height = 30;
    private static final double SPEED = 3.0;
    private static final double ROTATION_SPEED = 0.05;
    private boolean isExploding = false;

    private boolean isUltimateReady = true;
    private long lastUltimateTime = 0;
    private static final long ULTIMATE_COOLDOWN = 5000; // 8 giây

    public PlayerTank(double x, double y, double angle, Color color) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.tamX = x + width / 2;
        this.tamY = y + height / 2;
        this.color = color;
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
        gc.setFill(Color.GRAY);
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
    }

    public void moveForWard(Map gameMap, Tank playerTank2, double WIDTH, double HEIGHT) {
        double newX = x + SPEED * Math.sin(angle);
        double newY = y - SPEED * Math.cos(angle);
        if(!gameMap.isCollisionWithWalls(new TankStub(newX, newY, width, height)) &&
                !isCollidingWithO(playerTank2, newX, newY) &&
                newX >= 0 && newX + width<= WIDTH && newY + height <= HEIGHT && newY >= 0) {
            x = newX;
            y = newY;
        }
        updateCenter();
    }

    public void moveBackward(Map gameMap, Tank playerTank2, double WIDTH, double HEIGHT) {
        double newX = x - SPEED * Math.sin(angle);
        double newY = y + SPEED * Math.cos(angle);
        if(!gameMap.isCollisionWithWalls(new TankStub(newX, newY, width, height)) &&
                !isCollidingWithO(playerTank2, newX, newY) && newX >= 0 &&
                newX + width <= WIDTH && newY + height <= HEIGHT && newY >= 0) {
            x = newX;
            y = newY;
        }
        updateCenter();
    }

    public void rotateLeft() {
        angle -= ROTATION_SPEED;
    }

    public void rotateRight() {
        angle += ROTATION_SPEED;
    }

    private void updateCenter() {
        this.tamX = x + width / 2;
        this.tamY = y + height / 2;
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
    public double getBarrelEndX() {
        return x + width / 2 + (height / 2 * Math.sin(angle));
    }

    public double getBarrelEndY() {
        return y + height / 2 - (height / 2 * Math.cos(angle));
    }
    public void shoot() {
        if (!isShooting) {
            double bulletX = this.getBarrelEndX();
            double bulletY = this.getBarrelEndY();
            double bulletAngle = this.angle;
            bullet = new Bullet(bulletX, bulletY, bulletAngle, 1.0, 5.0, Color.DARKGREEN);
            isShooting = true;
        }
    }
    public void moveBullet(Map gameMap) {
        bullet.moveBullet();
        if (bullet.getDistanceTraveled() > 250 || gameMap.isBulletCollision(bullet)) {
            isShooting = false;
        }
    }

    private boolean isCollidingWithAi(AiTank aiTank, double newX, double newY) {
        Rectangle2D newBounds = new Rectangle2D(newX, newY, width, height);
        return newBounds.intersects(aiTank.getBounds());
    }
    private boolean isCollidingWithO(Tank playerTank2, double newX, double newY) {
        Rectangle2D newBounds = new Rectangle2D(newX, newY, width, height);
        return newBounds.intersects(playerTank2.getBounds());
    }

    public void useUltimate() {
        if (isUltimateReady) {
            // Gán các thuộc tính ultimate
            double bulletX = this.getBarrelEndX();
            double bulletY = this.getBarrelEndY();
            double bulletAngle = this.angle;
            bullet = new Bullet(bulletX, bulletY, bulletAngle, 4, 10.0, Color.RED);
            isShooting = true;

            // Đặt thời gian cuối sử dụng ultimate và trạng thái hồi chiêu
            lastUltimateTime = System.currentTimeMillis();
            isUltimateReady = false;
        }
    }

    public void update(long currentTime) {
        if (!isUltimateReady && (currentTime - lastUltimateTime >= ULTIMATE_COOLDOWN)) {
            isUltimateReady = true;
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

        // Số lượng khung hình để vẽ trong thời gian 1 giây
        int numFrames = 30;
        // Thời gian mỗi khung hình
        long frameDuration = EXPLOSION_DURATION / numFrames;
        // Thời gian đã trôi qua từ thời điểm bắt đầu vụ nổ
        long timeSinceStart = currentTime - explosionStartTime;
        // Số khung hình đã vẽ
        int currentFrame = (int) (timeSinceStart / frameDuration);

        // Tính kích thước của vụ nổ dựa trên thời gian đã trôi qua
        double explosionProgress = (double) timeSinceStart / EXPLOSION_DURATION;
        double maxExplosionSize = width * 4; // Kích thước vụ nổ lớn nhất
        double explosionSize = maxExplosionSize * explosionProgress;

        // Vẽ từng khung hình của vụ nổ
        for (int i = 0; i < currentFrame; i++) {
            double frameProgress = (double) i / numFrames;
            double frameExplosionSize = maxExplosionSize * frameProgress;
            double frameAngleOffset = 360.0 / numFrames * i;

            gc.setFill(Color.ORANGE);
            gc.fillOval(x - frameExplosionSize / 2, y - frameExplosionSize / 2, frameExplosionSize, frameExplosionSize);

            gc.setFill(Color.RED);
            gc.fillArc(x - frameExplosionSize / 2, y - frameExplosionSize / 2, frameExplosionSize, frameExplosionSize, 45 + frameAngleOffset, 270, ArcType.ROUND);

            // Vẽ tia lửa cho mỗi khung hình
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
    public boolean getIsExploding() {
        return isExploding;
    }

    private class TankStub extends Tank {
        public TankStub(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
