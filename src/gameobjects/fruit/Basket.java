package gameobjects.fruit;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import gameobjects.Consumable;
import gameobjects.Entity;
import player.Player;

public class Basket implements Entity, Consumable{
	private int x;
    private int y;
    private static int width;
    private static int height;
    private Image[] image;
    private ArrayList<Fruit> fruits;

	public Basket(int x, int y, Fruit fruit1, Fruit fruit2) {
		this.x = x;
    	this.y = y;
    	width = 28;
    	height = 28;
    	image = new Image[1];
    	fruits = new ArrayList<Fruit>();
    	fruits.add(fruit1);
    	fruits.add(fruit2);
    	
    	// verifies which pair of frutis it is in order to set the right image
    	boolean banana = false, apple = false, blueberry = false;
    	if(fruit1 instanceof Apple || fruit2 instanceof Apple)
    		apple = true;;
    	if(fruit1 instanceof Banana || fruit2 instanceof Banana)
    		banana = true;
    	if(fruit1 instanceof Blueberry || fruit2 instanceof Blueberry)
    		blueberry = true;
    	
    	if(apple && banana)
    		this.loadImage("src/images/fruits/apple_banana.png", 0);
    	else if(apple && blueberry)
    		this.loadImage("src/images/fruits/apple_blueberry.png", 0);
    	else if(banana && blueberry)
    		this.loadImage("src/images/fruits/banana_blueberry.png", 0);
	}
	
	public Basket(int x, int y, Fruit fruit1, Fruit fruit2, Fruit fruit3) {
		this.x = x;
    	this.y = y;
    	width = 28;
    	height = 28;
    	image = new Image[1];
    	fruits = new ArrayList<Fruit>();
    	fruits.add(fruit1);
    	fruits.add(fruit2);
    	fruits.add(fruit3);	
    	this.loadImage("src/images/fruits/basket.png", 0);
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

	public ArrayList<Fruit> getFruits() {
		return fruits;
	}

	public void setFruits(ArrayList<Fruit> fruits) {
		this.fruits = fruits;
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
    
    //Draws the fruit on the screen
	public void drawOnScreen(Graphics graphics) {
		graphics.drawImage(this.getImage(), this.getX(), this.getY(), null);
	}

	@Override
	public void fortify(Player player) {
		for (Fruit fruit : fruits) {
			fruit.fortify(player);
		}
	}

}
