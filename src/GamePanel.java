import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import directions.DirectionsEnum;
import gameobjects.Interactable;
import gameobjects.enemy.BlueEnemy;
import gameobjects.enemy.Enemy;
import gameobjects.fruit.Apple;
import gameobjects.fruit.Fruit;
import player.Player;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 768;
	static final int SCREEN_HEIGHT = 768;
	
	static final int UNIT_SIZE = 32;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	
	static final int DELAY = 34; // ( 1 second / 30 frames) * 1000 milliseconds
	
	static long score = 0;
	static int scoreMultiplier = 1;
	static int missMultiplier;
	
	List<Interactable> enemyList =  new ArrayList<Interactable>();
	List<Interactable> fruitList = new ArrayList<Interactable>();
	List<Interactable> fruitCollected = new ArrayList<Interactable>();
	Player player = new Player();
	DirectionsEnum direction = DirectionsEnum.EAST;
	boolean playerTouchedLeftBorder = true, playerTouchedRightBorder = true, playerTouchedTopBorder = true, playerTouchedDownBorder = true;
	
	boolean running = false;
	
	Timer timer;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		// Here we must read from the Levels.txt file and setup the level's enemy list and fruit list as well as the time for the level and the missMultiplier
		
		missMultiplier = 1;
		
		Enemy firstEnemy = new BlueEnemy(0, 5 * UNIT_SIZE);
		this.enemyList.add(firstEnemy);
		
		Fruit firstFruit = new Apple(5 * UNIT_SIZE, 5 * UNIT_SIZE);
		this.fruitList.add(firstFruit);
		
		startGame();
	}
	
	public void startGame() {
		running = true;
		timer = new Timer(DELAY, this);
		// performs an action detected by actionPerformed() (? don't actually know :P)
 		timer.start();
	}
	
	// I think this method gets called every time repaint() is called on actionPerformed()
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon ii = new ImageIcon("src/images/background/background.png");
		Image image = ii.getImage();
		g.drawImage(image, 0, 0, null);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if (!running) {
			gameOver(g);
			return;
		}
		
		// draws Game grid
//		for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
//			g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//			g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//		}
		
		// draws player
		player.draw(g);
		
		// move player
		boolean playerNotTouchingWall = (!playerTouchedLeftBorder  && direction == DirectionsEnum.WEST) || 
										(!playerTouchedTopBorder   && direction == DirectionsEnum.NORTH)|| 
										(!playerTouchedRightBorder && direction == DirectionsEnum.EAST) ||
										(!playerTouchedDownBorder  && direction == DirectionsEnum.SOUTH);
		if (playerNotTouchingWall) player.move(direction);
		
		// draws and updates all enemies
		for (Interactable enemy: this.enemyList) {
			enemy.draw(g);
			((Enemy) enemy).move();
		}
		
		// draws all fruits
		for (Interactable fruit: this.fruitList) {
			fruit.draw(g);
		}
		
		// draws game's UI
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		String scoreStr = "Score: " + score;
		g.drawString(scoreStr, (SCREEN_WIDTH - metrics.stringWidth(scoreStr))/2, 2 * UNIT_SIZE);
		drawPlayerHP(g, player.getTotalHP(), player.getCurrentHP());
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		metrics = getFontMetrics(g.getFont());
		g.drawString("Fruits:", (SCREEN_WIDTH - metrics.stringWidth("Fruits: ")), UNIT_SIZE);
		g.setFont(new Font("Ink Free", Font.BOLD, 30));
		g.drawString("x" + scoreMultiplier, (SCREEN_WIDTH - metrics.stringWidth("Fruits: ")), UNIT_SIZE * 2);
	}
	
	public void drawPlayerHP(Graphics g, int totalHP, int currentHP) {
		ImageIcon ii;
		ii = new ImageIcon("src/images/healthpoints/full_heart.png");
		Image fullHeart = ii.getImage();
		
		ii = new ImageIcon("src/images/healthpoints/empty_heart.png");
		Image emptyHeart = ii.getImage();
		
		int heartOffset = 0;
		for (int i = 0; i < currentHP; i++) {
			g.drawImage(fullHeart, UNIT_SIZE * heartOffset++, UNIT_SIZE/2, null);
		} for (int i = 0; i < totalHP - currentHP; i++) {
			g.drawImage(emptyHeart, UNIT_SIZE * heartOffset++, UNIT_SIZE/2, null);
		}
	}
	
	public void checkCollisions() {		
		// checks if player collided with any enemy
		Interactable obj = player.checkCollisions(this.enemyList);
		if (obj != null) {
			player.loseHP(1);
			if (player.getCurrentHP() <= 0) running = false;
			score -= 100 * missMultiplier;
			score = Math.max(0, score);
			scoreMultiplier = 1;
		}
		
		// checks if any enemy hit the map border and calls for the appropriate behavior of said enemy
		for (Interactable enemy: this.enemyList) {
			Enemy typeCastedEnemy = (Enemy) enemy;
			int enemyX = typeCastedEnemy.getX();
			int enemyY = typeCastedEnemy.getY();
			int enemyWidth = typeCastedEnemy.getWidth();
			int enemyHeight = typeCastedEnemy.getHeight();
			
			boolean touchedBorder = (enemyX < 0 || enemyX + enemyWidth > SCREEN_WIDTH || enemyY < 0 || enemyY + enemyHeight > SCREEN_HEIGHT);
			
			if (touchedBorder) typeCastedEnemy.hitWall();
		}
		
		// checks if player is or isn't touching any border
		int playerX = player.getX();
		int playerY = player.getY();
		int playerWidth = player.getWidth();
		int playerHeight = player.getHeight();
		playerTouchedLeftBorder = playerX <= 0 ;
		playerTouchedRightBorder = playerX + playerWidth >= SCREEN_WIDTH;
		playerTouchedTopBorder = playerY <= 0;
		playerTouchedDownBorder = playerY + playerHeight >= SCREEN_HEIGHT;
		
		// checks if any fruit "hit" the player
		// this list is used to avoid a ConcurrentMOdificationException
		List<Interactable> remove = new  ArrayList<Interactable>();
		for (Interactable fruit: this.fruitList) {
			boolean touchedPlayer = fruit.checkCollision(playerX, playerY, playerWidth, playerHeight);
			if (touchedPlayer) {
				remove.add(fruit);
				fruitCollected.add(fruit);
				scoreMultiplier *= ((Fruit) fruit).getScoreMultiplier();
				player.buff();
			}
		}
		this.fruitList.removeAll(remove);
		
		if (!running) timer.stop();
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		g.setFont(new Font("Ink Free", Font.BOLD, 35));
		String scoreString = score + " pts";
		metrics = getFontMetrics(g.getFont());
		g.drawString(scoreString, (SCREEN_WIDTH - metrics.stringWidth(scoreString))/2, (SCREEN_HEIGHT/2) + (35*2));
		drawCollectedFruits(g);
	}
	
	public void drawCollectedFruits(Graphics g) {
		int howManyFruits = fruitCollected.size();
		int fruitOffset = 0;
		int MAX_FRUITS_SHOWED = 5;
		Fruit fruit = null;
		for (int i = 0; i < Math.min(howManyFruits, MAX_FRUITS_SHOWED); i++) {
			fruit = ((Fruit) fruitCollected.get(i));
			fruit.setY((SCREEN_HEIGHT/2) + (35*2) + fruit.getHeight());
			fruit.setX((int)(((SCREEN_WIDTH/2)) - ((2.5-fruitOffset++) * fruit.getWidth())));
			fruit.draw(g);
		}
		
		if (howManyFruits > MAX_FRUITS_SHOWED) {
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			g.drawString("+" + (howManyFruits - MAX_FRUITS_SHOWED), (int)(((SCREEN_WIDTH/2)) - ((2.5-fruitOffset++) * fruit.getWidth())), (SCREEN_HEIGHT/2) + (30*2) + (2*fruit.getHeight()));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			score += 1 * scoreMultiplier;
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					direction = DirectionsEnum.WEST;
					player.move();
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					direction = DirectionsEnum.EAST;
					player.move();
					break;
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					direction = DirectionsEnum.NORTH;
					player.move();
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					direction = DirectionsEnum.SOUTH;
					player.move();
					break;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction == DirectionsEnum.WEST) player.stop();
					break;
				case KeyEvent.VK_RIGHT:
					if (direction == DirectionsEnum.EAST) player.stop();
					break;
				case KeyEvent.VK_UP:
					if (direction == DirectionsEnum.NORTH) player.stop();
					break;
				case KeyEvent.VK_DOWN:
					if (direction == DirectionsEnum.SOUTH) player.stop();;
					break;
			}
		}
	}
	
}
