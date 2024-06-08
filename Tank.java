import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public abstract class Tank {
    protected double x, y, width, height, angle;
    public boolean isShooting = false;
    protected Color color;
    protected double hp;
    public Bullet bullet;
    protected long explosionStartTime;
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

    public double getHp() {
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
    public long getExplosionStartTime() {
        return explosionStartTime;
    }
    public void setExplosionStartTime(long time) {
        this.explosionStartTime = time;
    }

    public boolean isDestroyed() {
        return hp <= 0;
    }
}
