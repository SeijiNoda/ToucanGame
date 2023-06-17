package gameobjects.fruit;

import java.awt.Graphics;

public class Apple extends Fruit {
	public Apple(int x, int y) {
		super(x, y);
		this.loadImage("src/images/fruits/apple.png");
		this.setHeight(this.image.getWidth(null));
		this.setWidth(this.image.getWidth(null));
		this.setScoreMultiplier(2);
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, this.getX(), this.getY(), null);
	}	
}
