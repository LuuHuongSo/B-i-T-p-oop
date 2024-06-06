import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public abstract class Tank {
    protected double x, y, width, height, angle;
    public boolean isShooting = false;
    protected Color color;
    protected int hp = 9;
    public Bullet bullet;
    protected long explosionStartTime = 0;
    protected static final long EXPLOSION_DURATION = 1500; // 1.5 seconds

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getAngle() {
        return angle;
    }

    public int getHp() {
        return hp;
    }

    public void reduceHp(double damage) {
        if (hp > 0) {
            hp -= damage;
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDestroyed() {
        return hp <= 0;
    }

    public void drawExplosion(GraphicsContext gc, long currentTime) {
        if (!this.isDestroyed()) return;

        long elapsed = currentTime - explosionStartTime;
        if (elapsed > EXPLOSION_DURATION) return;

        double explosionSize = width * 2 * (elapsed / (double) EXPLOSION_DURATION);

        gc.setFill(Color.ORANGE);
        gc.fillOval(x - explosionSize / 2, y - explosionSize / 2, explosionSize, explosionSize);

        gc.setFill(Color.RED);
        gc.fillArc(x - explosionSize / 2, y - explosionSize / 2, explosionSize, explosionSize, 45, 270, ArcType.ROUND);

        // Draw sparks
        int numSparks = 20;
        double sparkLength = 20;
        for (int i = 0; i < numSparks; i++) {
            double sparkAngle = 2 * Math.PI * i / numSparks;
            double sparkX = x + (explosionSize / 2) * Math.cos(sparkAngle);
            double sparkY = y + (explosionSize / 2) * Math.sin(sparkAngle);
            gc.setStroke(Color.YELLOW);
            gc.strokeLine(x, y, sparkX, sparkY);
        }
    }

    public void setExplosionStartTime(long explosionStartTime) {
        this.explosionStartTime = explosionStartTime;
    }
}
