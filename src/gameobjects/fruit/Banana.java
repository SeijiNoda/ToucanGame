package gameobjects.fruit;

import java.awt.Graphics;

public class Banana extends Fruit {
	public Banana(int x, int y) {
		super(x, y);
		this.loadImage("src/images/fruits/banana.png");
		this.setHeight(this.image.getWidth(null));
		this.setWidth(this.image.getWidth(null));
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, this.getX(), this.getY(), null);
	}	
}
