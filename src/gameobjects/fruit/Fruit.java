package gameObjects.fruit;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import gameObjects.Consumable;
import gameObjects.Entity;
import player.Player;

public abstract class Fruit implements Entity, Consumable{
    private int x;
    private int y;
    private static int width;
    private static int height;
    private Image[] image;
    
    public Fruit (int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.image = new Image[1];
    	width = 28;
    	height = 28;
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Image getImage() {
		return image[0];
	}
	
    // Check the collision between the fruit and other character
    public boolean checkCollision (int x, int y, int width, int height) {
        int dx = Math.abs(this.getX() - x);
        int dy = Math.abs(this.getY() - y);

        boolean collisionX = this.getWidth() + width > 2f * dx;
        boolean collisionY = this.getHeight() + height > 2f * dy;

        return collisionX && collisionY;
    }
    
    // Load the image and pass it to the desired index on the image vector
    public void loadImage (String filename, int index) {
        ImageIcon imageIcon;

        imageIcon = new ImageIcon(filename);
        this.image[index] = imageIcon.getImage();
    }
    
    // Draws the fruit on the screen
	public void drawOnScreen(Graphics graphics) {
		graphics.drawImage(this.getImage(), this.getX(), this.getY(), null);
	}
	
	public abstract void fortify (Player player);
    
}
