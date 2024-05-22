import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class Map {

    private ArrayList<Wall> walls;

    public Map() {
        walls = new ArrayList<>();
        initializeMap();
    }

    private void initializeMap() {
        walls.add(new Wall(100, 100, 100, 20));
        walls.add(new Wall(100, 120, 20, 80));
        walls.add(new Wall(600, 100, 100, 20));
        walls.add(new Wall(680, 120, 20, 80));
        walls.add(new Wall(100, 400, 20, 80));
        walls.add(new Wall(100, 480, 100, 20));
        walls.add(new Wall(680, 400, 20, 80));
        walls.add(new Wall(600, 480, 100, 20));
        walls.add(new Wall(290, 90, 20, 60));
        walls.add(new Wall(490, 90, 20, 60));
        walls.add(new Wall(310, 130, 180, 20));
        walls.add(new Wall(290, 450, 20, 60));
        walls.add(new Wall(490, 450, 20, 60));
        walls.add(new Wall(310, 450, 180, 20));
    }

    public void draw(GraphicsContext gc) {
        for (Wall wall : walls) {
            wall.drawWall(gc);
        }
    }

    public boolean isCollisionWithWalls(Tank tank) {
        for (Wall wall : walls) {
            if (tank.getBounds().intersects(wall.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public boolean isBulletCollision(Bullet bullet) {
        for (Wall wall : walls) {
            if (bullet.getX() >= wall.getToaDoX() && bullet.getX() <= wall.getToaDoX() + wall.getDai() && bullet.getY() >= wall.getToaDoY() && bullet.getY() <= wall.getToaDoY() + wall.getRong()) {
                return true;
            }
        }
        return false;
    }
}
