import javafx.geometry.Rectangle2D;

public abstract class Tank {
    protected double x, y, width, height, angle;
    public boolean isShooting = false;
    protected int hp = 3;
    public Bullet bullet;

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
    
    public void reduceHp() {
        if (hp > 0) {
            hp--;
        }
    }
    
    public boolean isDestroyed() {
        return hp <= 0;
    }
}
