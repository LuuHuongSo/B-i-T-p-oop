
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall {
    private int toadoX;
    private int toadoY;
    private int dai;
    private int rong;


    public Wall(int x, int y, int dai, int rong) {
        this.toadoX = x;
        this.toadoY = y;
        this.dai = dai;
        this.rong = rong;
    }

    public void drawWall(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(toadoX, toadoY, dai, rong);
    }

    public int getToaDoX() {
        return toadoX;
    }
    public int getToaDoY() {
        return toadoY;
    }
    public int getDai() {
        return dai;
    }
    public int getRong() {
        return rong;
    }

    /*public boolean tuongChanTren(ArrayList<Wall> a, int x, int y, int size) {
        for(Wall wall : a) {
            if(y == wall.toadoY + wall.rong && x + size > wall.toadoX && x < wall.toadoX + wall.dai) return true;
        }
        return false;
    }
    public boolean tuongChanDuoi(ArrayList<Wall> a, int x, int y, int size) {
        for(Wall wall : a) {
            if(y + size == wall.toadoY && x + size > wall.toadoX && x < wall.toadoX + wall.dai) return true;
        }
        return false;
    }
    public boolean tuongChanTrai(ArrayList<Wall> a, int x, int y, int size) {
        for(Wall wall : a) {
            if(x == wall.toadoX + wall.dai && y + size > wall.toadoY && y < wall.toadoY + wall.rong) return true;
        }
        return false;
    }
    public boolean tuongChanPhai(ArrayList<Wall> a, int x, int y, int size) {
        for(Wall wall : a) {
            if(x + size == wall.toadoX && y + size > wall.toadoY && y < wall.toadoY + wall.rong) return true;
        }
        return false;
    }*/
}
