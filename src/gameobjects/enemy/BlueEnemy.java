package gameobjects.enemy;

import java.awt.Color;
import java.awt.Graphics;

import directions.DirectionsEnum;

public class BlueEnemy extends Enemy{
	private static int SPEED = 32;
	private static int HEIGHT = 32;
	private static int WIDTH = 32;
	
	private DirectionsEnum direction = DirectionsEnum.EAST;
	
	private Color color = Color.BLUE;
	
	public BlueEnemy() {
		this.setSpeed(SPEED);
		this.setHeight(HEIGHT);
		this.setWidth(WIDTH);
	}
	
	public BlueEnemy(int x, int y) {
		this.setSpeed(SPEED);
		this.setHeight(HEIGHT);
		this.setWidth(WIDTH);
		this.setX(x);
		this.setY(y);
	}
	
	public void draw(Graphics g) {
		Color originalGraphicsColor = g.getColor();
		g.setColor(this.color);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g.setColor(originalGraphicsColor);
	}
	
	public void move() {
		switch(this.direction) {
			case NORTH: 
				this.setY(this.getY() - SPEED);;
				break;
			case SOUTH: 
				this.setY(this.getY() + SPEED);;
				break;
			case WEST:
				this.setX(this.getX() - SPEED);;
				break;
			case EAST:
				this.setX(this.getX() + SPEED);;
				break;
		}
	}
	
	public boolean checkCollision(int x, int y, int width, int height) {
		boolean collision = this.getX() < x + width &&
						    this.getX() + this.getWidth() > x &&
						    this.getY() < y + height &&
						    this.getY() + this.getHeight() > y;
		
		return collision;
	}
	
	public void hitWall() {
		direction = DirectionsEnum.flip(direction);
	}
}
