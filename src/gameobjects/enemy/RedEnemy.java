package gameobjects.enemy;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import directions.DirectionsEnum;
import player.Player;

public class RedEnemy extends Enemy{
	private static int SPEED = 8;
	private static int HEIGHT = 32;
	private static int WIDTH = 32;
	
	private DirectionsEnum direction = DirectionsEnum.EAST;
	
	private Image[] imagesRight = new Image[2];
	private Image[] imagesLeft = new Image[2];
	private int frame = 0;
	
	private void loadImages(String imagesDirectory) {
		ImageIcon ii;
		// sprite for when enemy is facing East
		ii = new ImageIcon(imagesDirectory + "red_0.png");
		this.imagesRight[0] = ii.getImage();
		ii = new ImageIcon(imagesDirectory + "red_1.png");
		this.imagesRight[1] = ii.getImage();
		
		// sprite for when enemy is facing West
		ii = new ImageIcon(imagesDirectory + "red_2.png");
		this.imagesLeft[0] = ii.getImage();
		ii = new ImageIcon(imagesDirectory + "red_3.png");
		this.imagesLeft[1] = ii.getImage();
	}
	
	public RedEnemy() {
		this.setSpeed(SPEED);
		this.setHeight(HEIGHT);
		this.setWidth(WIDTH);
		loadImages("src/images/enemies/");
	}
	
	public RedEnemy(int x, int y) {
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
	
	public void move(Player player) {
        int dx = this.getX() - player.getX();
        int dy = this.getY() - player.getY();

        if (Math.abs(dx) < Math.abs(dy)) {
            this.direction = dy > 0 ? DirectionsEnum.NORTH : DirectionsEnum.SOUTH;
        } else {
            this.direction = dx > 0 ? DirectionsEnum.WEST : DirectionsEnum.EAST;
        }

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
