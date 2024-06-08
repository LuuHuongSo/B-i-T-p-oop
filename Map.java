
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Map {

    private final ArrayList<Wall> walls;

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
        walls.add(new Wall(240, 250, 20, 100));
        walls.add(new Wall(540, 250, 20, 100));
        //walls.add(new Wall())
    }

    public void draw(GraphicsContext gc) {
        // Draw the desert background
        drawDesertBackground(gc);

        // Draw the walls
        for (Wall wall : walls) {
            wall.drawWall(gc);
        }
    }

    private void drawDesertBackground(GraphicsContext gc) {
        gc.setFill(Color.BURLYWOOD);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Dunes
        gc.setFill(Color.SANDYBROWN);
        gc.fillPolygon(new double[]{50, 150, 250}, new double[]{300, 250, 300}, 3);
        gc.fillPolygon(new double[]{400, 500, 600}, new double[]{400, 350, 400}, 3);
        gc.fillPolygon(new double[]{200, 300, 400}, new double[]{500, 450, 500}, 3);

        // Optionally, add some cacti or rocks (simple shapes)
        gc.setFill(Color.GREEN);
        gc.fillRect(70, 330, 10, 40);
        gc.fillRect(80, 340, 10, 20);
        gc.fillRect(470, 430, 10, 40);

        gc.setFill(Color.DARKGREY);
        gc.fillOval(300, 370, 20, 10);
        gc.fillOval(320, 380, 30, 15);
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
