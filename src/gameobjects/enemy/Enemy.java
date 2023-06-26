package gameObjects.enemy;

import java.awt.Image;
import javax.swing.ImageIcon;
import directions.Directions;
import gameObjects.Entity;
import player.Player;

public abstract class Enemy implements Entity {
	protected int x;
	protected int y;

    protected int speed;
    protected int width;
    protected int height;

    private Image[] images = new Image[4];
    private int frameCounter = 0;

    private Directions direction = Directions.STOPPED;
    private Directions movementSide = Directions.E;

    // Pos x getter
    public int getX () {
        return this.x;
    }

    // Pos x setter
    public void setX (int x) {
        this.x = x;
    }

    // Pos y getter
    public int getY () {
        return this.y;
    }

    // Pos y setter
    public void setY (int y) {
        this.y = y;
    }

    // Speed getter
    public int getSpeed () {
        return this.speed;
    }

    // Speed setter
    public void setSpeed (int speed) {
        this.speed = speed;
    }

    // Width getter
    public int getWidth () {
        return this.width;
    }

    // Width setter
    public void setWidth (int width) {
        this.width = width;
    }

    // Height getter
    public int getHeight () {
        return this.height;
    }

    // Height setter
    public void setHeight (int height) {
        this.height = height;
    }

    // Frame counter getter
    public int getFrameCounter () {
        return this.frameCounter;
    }

    // Frame counter increment
    public void incrementFrameCounter () {
        this.frameCounter += 1;
    }

    // Direction getter
    public Directions getDirection () {
        return this.direction;
    }

    // Direction setter
    public void setDirection (Directions direction) {
        this.direction = direction;
    }

    // Movement side getter
    public Directions getMovementSide () {
        return this.movementSide;
    }

    // Movement side setter
    public void setMovementSide (Directions side) {
        this.movementSide = side;
    }

    // Specific image getter
    public Image getImage (int index) {
        return this.images[index];
    }

    // Load the image and pass it to the desired index on the image vector
    public void loadImage (String filename, int index) {
        ImageIcon imageIcon;

        imageIcon = new ImageIcon(filename);
        this.images[index] = imageIcon.getImage();
    }

    // Check the collision between the enemy and other character
    public boolean checkCollision (int x, int y, int width, int height) {
        int dx = Math.abs(this.getX() - x);
        int dy = Math.abs(this.getY() - y);

        boolean collisionX = this.getWidth() + width > 2f * dx;
        boolean collisionY = this.getHeight() + height > 2f * dy;

        return collisionX && collisionY;
    }

    // method to make the enemy movement action
    public abstract void move (Player player);
}
