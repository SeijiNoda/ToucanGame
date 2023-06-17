package gameobjects.fruit;

import java.awt.Image;

import javax.swing.ImageIcon;

import gameobjects.Interactable;

public abstract class Fruit implements Interactable {
	private int x = 0, y = 0;
	private int width =  0, height = 0;
	
	private int scoreMultiplier = 1;
	
	protected Image image;
	
	public Fruit(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected void loadImage(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		this.image = ii.getImage();
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
	
	protected void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	protected void setHeight(int height) {
		this.height = height;
	}

	public double getScoreMultiplier() {
		return this.scoreMultiplier;
	}

	public void setScoreMultiplier(int scoreMultiplier) {
		this.scoreMultiplier = scoreMultiplier;
	}
	
	public boolean checkCollision(int x, int y, int width, int height) {
		boolean collision = this.getX() < x + width &&
			    this.getX() + this.getWidth() > x &&
			    this.getY() < y + height &&
			    this.getY() + this.getHeight() > y;

	    return collision;
	}
}
