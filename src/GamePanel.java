import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
    Timer timer;
    Random generator;
    FontMetrics metrics;

    boolean running = true;

    int[] keyPressedMap = {0, 0, 0, 0};

    static final int screenWidth = 768;
    static final int screenHeight = 768;

    static final int delay = 10;   // 100 FPS

    static long score = 0;
    static int scoreMultiplier = 1;

    Player player = new Player(368, 368);
    List<Enemy> enemies =  new ArrayList<Enemy>();

    static int redEnemiesCounter = 0;

    GamePanel() {
        this.generator = new Random();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        BlueEnemy blueEnemy = new BlueEnemy(240, 240);
        blueEnemy.setDirection(Directions.E);

        // First blue enemy
        this.enemies.add(blueEnemy);

        blueEnemy = new BlueEnemy(496, 496);
        blueEnemy.setDirection(Directions.E);

        // Second blue enemy
        this.enemies.add(blueEnemy);

        startGame();
    }

    // Start the game properly
    public void startGame() {
        this.running = true;
        this.timer = new Timer(delay, this);
        this.timer.start();
    }

    // Game over window
    public void gameOver(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, screenWidth, screenHeight);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 75));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER", (screenWidth - metrics.stringWidth("GAME OVER"))/2, screenHeight/2);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 35));
        String scoreString = score + " pts";
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString(scoreString, (screenWidth - metrics.stringWidth(scoreString))/2, (screenHeight/2) + 70);
        // drawCollectedFruits(graphics);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        ImageIcon imageIcon = new ImageIcon("src/images/background/background.png");

        graphics.drawImage(imageIcon.getImage(), 0, 0, null);
        drawOnScreen(graphics);
    }

    public void drawOnScreen(Graphics graphics) {
        if (!this.running) {
            gameOver(graphics);
            return;
        }

        // Key pressed map to turn more dynamic the player's movement
        if (keyPressedMap[0] == 1) {
            this.player.setDirection(Directions.N);
        } else if (keyPressedMap[1] == 1) {
            this.player.setDirection(Directions.S);
        } else if (keyPressedMap[2] == 1) {
            this.player.setMovementSide(Directions.W);
            this.player.setDirection(Directions.W);
        } else if (keyPressedMap[3] == 1) {
            this.player.setMovementSide(Directions.E);
            this.player.setDirection(Directions.E);
        } else {
            this.player.setDirection(Directions.STOPPED);
        }

        // Spawn a new red enemy for each 500 points
        if (score / 500 > redEnemiesCounter) {
            RedEnemy redEnemy = new RedEnemy(368, 736);
            this.enemies.add(redEnemy);
            
            redEnemiesCounter = (int) (score / 500);
        }

        // Move and draw each enemy
        for (Enemy enemy: this.enemies) {
            enemy.move(player);
            enemy.drawOnScreen(graphics);
        }

        player.move();
        player.drawOnScreen(graphics);
        drawPlayerHP(graphics);

        // Score text
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 32));
        graphics.drawString("Score: " + score, 8, 32);

        // Fruits text
        graphics.setFont(new Font("Ink Free", Font.BOLD, 20));
        this.metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Fruits:", (screenWidth - metrics.stringWidth("Fruits: ") - 4), 24);

        // Multiplier text
        graphics.setFont(new Font("Ink Free", Font.BOLD, 30));
        this.metrics = getFontMetrics(graphics.getFont());
        String multiplier = "x" + scoreMultiplier;
        graphics.drawString(multiplier, (screenWidth - metrics.stringWidth(multiplier)) - 8, 52);
    }

    // Draw player health points in the top middle of the screen
    public void drawPlayerHP (Graphics graphics) {
        ImageIcon imageIcon = new ImageIcon("src/images/healthPoints/full.png");
        Image fullHeart = imageIcon.getImage();

        imageIcon = new ImageIcon("src/images/healthpoints/empty.png");
        Image emptyHeart = imageIcon.getImage();

        // Compute the offset to center the hearts
        int heartOffset = (int) Math.round(0.5f * screenWidth - player.getTotalHP() * 16f);

        // Draw first the full hearts
        for (int i = 0; i < player.getCurrentHP(); i++) {
            graphics.drawImage(fullHeart, heartOffset + 32 * i, 8, null);
        }

        // Draw finally the empty hearts
        for (int i = player.getCurrentHP(); i < player.getTotalHP(); i++) {
            graphics.drawImage(emptyHeart, heartOffset + 32 * i, 8, null);
        }
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            switch(event.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyPressedMap[0] = 1;
                    break;
                case KeyEvent.VK_DOWN:
                    keyPressedMap[1] = 1;
                    break;
                case KeyEvent.VK_LEFT:
                    keyPressedMap[2] = 1;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyPressedMap[3] = 1;
                    break;
            }
        }
        
        @Override
        public void keyReleased(KeyEvent event) {
            switch(event.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyPressedMap[0] = 0;
                    break;
                case KeyEvent.VK_DOWN:
                    keyPressedMap[1] = 0;
                    break;
                case KeyEvent.VK_LEFT:
                    keyPressedMap[2] = 0;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyPressedMap[3] = 0;
                    break;
            }
        }
    }

    // Check player-enemy and enemy-enemy collisions
    public void checkCollisions () {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            // Avoid errors
            if (enemy instanceof BlueEnemy || enemy == null) {
                continue;
            }

            for (int j = i+1; j < enemies.size(); j++) {
                Enemy other = enemies.get(j);

                if (other instanceof BlueEnemy || other == null) {
                    continue;
                }

                // Check the collision between two red enemies to merge them
                if (enemy.checkCollision(other.getX(), other.getY(), 0, 0)) {
                    enemy.setSpeed(enemy.getSpeed() + 1);
                    enemy.setSpeed(Math.min(3, enemy.getSpeed()));
                    this.enemies.set(j, null);
                }
            }
        }

        // Remove merged red enemies
        enemies.removeAll(Collections.singleton(null));

        for (Enemy enemy: this.enemies) {
            if (enemy == null) {
                continue;
            }

            // Check the collision between the player and some enemy
            if (enemy.checkCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                player.decrementCurrentHP();

                // Decrement player health points
                if (player.getCurrentHP() <= 0) {
                    this.running = false;
                }

                // Penalize the collision and reset the score multiplier
                score -= Math.min(100, score);
                scoreMultiplier = 1;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (this.running) {
            score += 1 * scoreMultiplier;
            checkCollisions();
        } else {
            this.timer.stop();
        }

        repaint();
    }
}
