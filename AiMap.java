import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AiMap {
    private ArrayList<Wall> walls2;

    public AiMap() {
        walls2 = new ArrayList<>();
        initializeMap();
    }
    public void initializeMap() {
        walls2.add(new Wall(0, 0, 10, 600));
        walls2.add(new Wall(790, 0, 10, 600));
        walls2.add(new Wall(10, 590, 780, 10));
    }
    public void draw(GraphicsContext gc) {
        // Draw the desert background
        drawDesertBackground(gc);

        // Draw the walls
        for (Wall wall : walls2) {
            wall.drawWall(gc);
        }
    }
    public void drawDesertBackground(GraphicsContext gc) {
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

        gc.setFill(Color.BLACK);
        gc.fillOval(300, 400, 200, 200);
    }
}
