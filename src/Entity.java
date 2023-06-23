import java.awt.Graphics;


public interface Entity {
    public void loadImage (String filename, int index);

    public boolean checkCollision (int x, int y, int width, int height);

    public void drawOnScreen (Graphics graphics);
}
