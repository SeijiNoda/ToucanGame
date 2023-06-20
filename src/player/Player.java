package player;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import java.util.List;

import gameobjects.Interactable;

import directions.DirectionsEnum;

public class Player {
	private int speed = 16, x = 0, y = 0;
	private int width, height;
	
	private int totalHP;
	private int currentHP;
	
	private DirectionsEnum direction;
	
	private Image[] imagesRight = new Image[2];
	private Image[] imagesLeft = new Image[2];
	private int frame = 0;
	
	private void loadImages(String imagesDirectory) {
		ImageIcon ii;
		
		// sprite for when player is facing East
		ii = new ImageIcon(imagesDirectory + "toucan_0.png");
		this.imagesRight[0] = ii.getImage();
		ii = new ImageIcon(imagesDirectory + "toucan_2.png");
		this.imagesRight[1] = ii.getImage();
		
		// sprite for when player is facing West
		ii = new ImageIcon(imagesDirectory + "toucan_1.png");
		this.imagesLeft[0] = ii.getImage();
		ii = new ImageIcon(imagesDirectory + "toucan_3.png");
		this.imagesLeft[1] = ii.getImage();
	}
	
	private boolean stopped = true;
	private boolean buffed = false;
	
	public Player() {
		this.direction = DirectionsEnum.EAST;
		loadImages("src/images/player/");
		this.width = this.imagesRight[0].getWidth(null);
		this.height = this.imagesRight[0].getHeight(null);
		this.totalHP = 3;
		this.currentHP = 3;
	}
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.direction = DirectionsEnum.EAST;
		loadImages("src/images/player/");
		this.width = this.imagesRight[0].getWidth(null);
		this.height = this.imagesRight[0].getHeight(null);
		this.totalHP = 3;
		this.currentHP = 3;
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
	
	public int getSpeed() {
		return !buffed ? speed : speed + 25;
	}
	
	public void buff() {
		this.buffed = true;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getTotalHP() {
		return totalHP;
	}

	public void setTotalHP(int totalHP) {
		this.totalHP = totalHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public void draw(Graphics g) {
		Image image = this.direction == DirectionsEnum.EAST ? imagesRight[frame++] : imagesLeft[frame++];
		if (frame == 2) frame = 0;
		g.drawImage(image, x, y, null);
	}
	
	public void move(DirectionsEnum newDirection) {
		this.direction = newDirection;
		
		if (stopped) return;
		
		switch(this.direction) {
			case NORTH:
				this.y -= speed;
				break;
			case EAST:
				this.x += speed;
				break;
			case SOUTH:
				this.y += speed;
				break;
			case WEST:
				this.x -= speed;
				break;
		}
	}
	
	public void move() {
		this.stopped = false;
	}
	
	public void stop() {
		this.stopped = true;
	}
	
	
	public void loseHP(int howMuch) {
		this.currentHP -= howMuch;
	}
	
	public Interactable checkCollisions(List<Interactable> gameObjects) {
		for (Interactable object: gameObjects) {
			if (object.checkCollision(this.x, this.y, this.width, this.height)) return object;
		}
		
		return null;
	}
}
