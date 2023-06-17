package gameobjects.fruit;

import java.util.List;
import java.awt.Graphics;

import gameobjects.Interactable;

public class Basket implements Interactable {
	private int x = 0, y = 0;
	private int width =  0, height = 0;
	private List<Fruit> fruits;
	private BasketTypesEnum type;
	
	public Basket(List<Fruit> fruits) {
		this.fruits = fruits;
		type = BasketTypesEnum.whichBasket(fruits);
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

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<Fruit> getFruits() {
		return fruits;
	}

	public void setFruits(List<Fruit> fruits) {
		this.fruits = fruits;
	}

	public BasketTypesEnum getType() {
		return type;
	}

	public void setType(BasketTypesEnum type) {
		this.type = type;
	}

	public boolean checkCollision(int x, int y, int width, int height) {
		boolean collision = this.getX() < x + width &&
			    this.getX() + this.getWidth() > x &&
			    this.getY() < y + height &&
			    this.getY() + this.getHeight() > y;

	    return collision;
	}

	public void draw(Graphics g) {
		g.drawImage(type.getImage(), this.getX(), this.getY(), null);
	}

}
