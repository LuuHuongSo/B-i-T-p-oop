import javafx.geometry.Rectangle2D;

public abstract class Tank {
    protected double x, y, width, height, angle;

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
}
