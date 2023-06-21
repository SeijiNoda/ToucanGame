package gameobjects.enemy;

import java.awt.Graphics;

import gameobjects.Interactable;

import player.Player;

public abstract class Enemy implements Interactable {
	private int speed, x = 0, y = 0;
	private int width = 25, height = 25;
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
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

	public abstract void move(Player player);
	
	public abstract void hitWall();
}
