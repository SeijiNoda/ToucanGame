package gameobjects.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import directions.DirectionsEnum;

public class BlueEnemy extends Enemy{
	private static int SPEED = 16;
	private static int HEIGHT = 32;
	private static int WIDTH = 32;
	
	private DirectionsEnum direction = DirectionsEnum.EAST;
	
	private Image[] imagesRight = new Image[2];
	private Image[] imagesLeft = new Image[2];
	private int frame = 0;
	
	private void loadImages(String imagesDirectory) {
		ImageIcon ii;
		// sprite for when enemy is facing East
		ii = new ImageIcon(imagesDirectory + "blue_0.png");
		this.imagesRight[0] = ii.getImage();
		ii = new ImageIcon(imagesDirectory + "blue_1.png");
		this.imagesRight[1] = ii.getImage();
		
		// sprite for when enemy is facing West
		ii = new ImageIcon(imagesDirectory + "blue_2.png");
		this.imagesLeft[0] = ii.getImage();
		ii = new ImageIcon(imagesDirectory + "blue_3.png");
		this.imagesLeft[1] = ii.getImage();
	}
	
	public BlueEnemy() {
		this.setSpeed(SPEED);
		this.setHeight(HEIGHT);
		this.setWidth(WIDTH);
		loadImages("src/images/enemies/");
	}
	
	public BlueEnemy(int x, int y) {
		this.setSpeed(SPEED);
		this.setHeight(HEIGHT);
		this.setWidth(WIDTH);
		this.setX(x);
		this.setY(y);
		loadImages("src/images/enemies/");
	}
	
	public void draw(Graphics g) {
		Image image = this.direction == DirectionsEnum.EAST ? imagesRight[frame++] : imagesLeft[frame++];
		if (frame == 2) frame = 0;
		g.drawImage(image, this.getX(), this.getY(), null);
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
