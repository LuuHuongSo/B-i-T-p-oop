import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerTank extends Tank {
    private double tamX;
    private double tamY;
    public double width = 20;
    public double height = 30;
    private static final double SPEED = 3.0;
    private static final double ROTATION_SPEED = 0.05;

    public PlayerTank(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.tamX = x + width / 2;
        this.tamY = y + height / 2;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(tamX, tamY);
        gc.rotate(Math.toDegrees(angle));
        gc.translate(-tamX, -tamY);

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);

        // Add some detail to the body
        gc.setFill(Color.GREEN);
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
        gc.fillRect(x - 15, y + 33, this.getHp() * 15, 5);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x - 15, y + 33, 45, 5);

        gc.restore();
    }

    public void moveForWard(Map gameMap, Tank playerTank2, double WIDTH, double HEIGHT) {
        double newX = x + SPEED * Math.sin(angle);
        double newY = y - SPEED * Math.cos(angle);
        if(!gameMap.isCollisionWithWalls(new TankStub(newX, newY, width, height)) && !isCollidingWithO(playerTank2, newX, newY) && newX >= 0 && newX + width<= WIDTH && newY + height <= HEIGHT && newY >= 0) {
            x = newX;
            y = newY;
        }
        updateCenter();
    }

    public void moveBackward(Map gameMap, Tank playerTank2, double WIDTH, double HEIGHT) {
        double newX = x - SPEED * Math.sin(angle);
        double newY = y + SPEED * Math.cos(angle);
        if(!gameMap.isCollisionWithWalls(new TankStub(newX, newY, width, height)) && !isCollidingWithO(playerTank2, newX, newY) && newX >= 0 && newX + width <= WIDTH && newY + height <= HEIGHT && newY >= 0) {
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
            bullet = new Bullet(bulletX, bulletY, bulletAngle);
            isShooting = true;
        }
    }
    public void moveBullet(Map gameMap, double WIDTH, double HEIGHT) {
        bullet.moveBullet();
        if (bullet.getDistanceTraveled() > 250 || gameMap.isBulletCollision(bullet)) {
            isShooting = false;
        }
    }
    //|| bullet.getX() < 0 || bullet.getX() > WIDTH || bullet.getY() < 0 || bullet.getY() > HEIGHT
    public void reduceHp(PlayerTank tank) {
        if(tank.bullet.isHit(this)) {
            hp --;
        }
    }
    private boolean isCollidingWithO(Tank playerTank2, double newX, double newY) {
        Rectangle2D newBounds = new Rectangle2D(newX, newY, width, height);
        return newBounds.intersects(playerTank2.getBounds());
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
