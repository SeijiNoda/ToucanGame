import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;


public class GamePanel extends JPanel implements ActionListener {
    Timer timer;
    Random generator;
    FontMetrics metrics;

    boolean running = true;

    int[] keyPressedMap = {0, 0, 0, 0};

    // Generated serial version ID
    private static final long serialVersionUID = -2083324232131080328L;

    private static final int screenWidth = 768;
    private static final int screenHeight = 768;

    private static final int delay = 10;   // 100 FPS

    private static String name;
    private static long score = 0;
    private static int scoreMultiplier = 1;

    private static boolean wrotePlayerScore = false;

    private Player player = new Player(368, 368);
    private List<Enemy> enemies =  new ArrayList<Enemy>();
    private ArrayList<Consumable> consumables = new ArrayList<Consumable>();

    static int redEnemiesCounter = 0;
    static int fruitsCounter = 0;

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

        getPlayerName();

        startGame();
    }

    // Ask the player's name
    private void getPlayerName() {
        String response = null;
        ImageIcon imageIcon = new ImageIcon("/src/images/player/1.png ");

        do {
            // Input dialog modal pops up on the screen
            response = (String) JOptionPane.showInputDialog(
                this, "Insert you name to play:", "Toucan Game", 1, imageIcon, null, null
            );

            // Player pressed "Cancel"
            if (response == null) {
                System.exit(0);
            }

            // Nothing was inserted and player pressed "OK"
            if (response.equals("")) {
                JOptionPane.showMessageDialog(
                    this, "Insert your name to play, please!\nIt will be used to register your score.",
                    "Error", JOptionPane.ERROR_MESSAGE
                );

                continue;
            }

            // The inserted name has non-alpha characters
            if (!((String) response).matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(
                    this, "Your name should only contain letters.\nTry again!",
                    "Error", JOptionPane.ERROR_MESSAGE
                );

                response = "";
            }

        } while (response.equals(""));

        name = (String) response;
    }

    // Start the game properly
    private void startGame() {
        this.running = true;
        this.timer = new Timer(delay, this);
        this.timer.start();
    }

    // Game over window
    private void gameOver(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, screenWidth, screenHeight);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 75));
        metrics = getFontMetrics(graphics.getFont());

        graphics.drawString(
            "GAME OVER", (screenWidth - metrics.stringWidth("GAME OVER")) / 2, screenHeight / 2
        );

        graphics.setFont(new Font("Ink Free", Font.BOLD, 35));
        
        metrics = getFontMetrics(graphics.getFont());
        String scoreString = score + " pts";

        graphics.drawString(
            scoreString, (screenWidth - metrics.stringWidth(scoreString)) / 2, (screenHeight / 2) + 70
        );

        if (!wrotePlayerScore) {
            addPlayerScore();
        }

        ArrayList<Score> scores = getScores();
        Score highestScore = getHighestScore(scores);

        graphics.setFont(new Font("Ink Free", Font.PLAIN, 35));
        metrics = getFontMetrics(graphics.getFont());

        graphics.drawString(
            "HIGHEST SCORE", (screenWidth - metrics.stringWidth("HIGHEST SCORE")) / 2, (screenHeight / 2) - 175
        );

        String highestScoreString = highestScore.getScore() + " by " + highestScore.getName();
        graphics.drawString(
            highestScoreString, (screenWidth - metrics.stringWidth(highestScoreString)) / 2, (screenHeight / 2) - 105
        );

        // drawCollectedFruits(graphics);
    }

    // Get a list of all registered scores
    private ArrayList<Score> getScores() {
        File file = new File("src/scores.txt");

        ArrayList<Score> scores = new ArrayList<Score>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] data = line.split(",");

                String name = data[0];
                long score = Long.parseLong(data[1]);

                // Create Score and add it to list
                scores.add(new Score(name, score));
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();

        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                
            } catch (Exception ex) {
                System.out.println("Error closing the BufferedReader");
            }
        }

        return scores;
    }

    // Add current player's name and score to the scores file
    private void addPlayerScore() {
        File file = new File("src/scores.txt");
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(name + "," + score + "\n");
            wrotePlayerScore = true;

        } catch (IOException ioe) {
            ioe.printStackTrace();

        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }

            } catch (Exception ex) {
                System.out.println("Error closing the BufferedWriter");
            }
        }
    }

    // Return the highest score from the list given
    private Score getHighestScore(ArrayList<Score> listScores) {
        Score highestScore = null;
        long highest = Integer.MIN_VALUE;

        for(Score score: listScores) {
            if(score.getScore() >= highest) {
                highest = score.getScore();
                highestScore = score;
            }
        }

        return highestScore;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        ImageIcon imageIcon = new ImageIcon("/src/images/background/background.png");

        graphics.drawImage(imageIcon.getImage(), 0, 0, null);
        drawOnScreen(graphics);
    }

    private void drawOnScreen(Graphics graphics) {
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
        
      
        // Spawn a new random fruit for each 250 points
        if (score / 250 > fruitsCounter) {
        	int x = generator.nextInt(740);
        	int y = generator.nextInt(740);
        	int chances = generator.nextInt(100);
        	if (chances <= 50) {
        		chances = generator.nextInt(3);
        		if (chances == 1) {
        			Apple fruit = new Apple(x, y);
        			consumables.add(fruit);
        		}
        		else if (chances == 2) {
        			Banana fruit = new Banana(x, y);
        			consumables.add(fruit);
        		}
        		else {
        			Blueberry fruit = new Blueberry(x, y);
        			consumables.add(fruit);
        		}
        	}
        	else if (chances > 50 && chances <= 75) {
        		chances = generator.nextInt(2);
        		if (chances == 1) {
        			Apple apple = new Apple(x, y);
        			Banana banana = new Banana(x, y);
        			Basket basket = new Basket(x, y, apple, banana);
        			consumables.add(basket);
        			
        		}
        		else {
        			Apple apple = new Apple(x, y);
        			Blueberry blueberry = new Blueberry(x, y);
        			Basket basket = new Basket(x, y, apple, blueberry);
        			consumables.add(basket);        		
        		}
        	}
        	else if (chances > 75 && chances < 90) {
    			Banana banana = new Banana(x, y);
    			Blueberry blueberry = new Blueberry(x, y);
    			Basket basket = new Basket(x, y, banana, blueberry);
    			consumables.add(basket);        		
        	}
        	else {
        		Apple apple = new Apple(x, y);
    			Banana banana = new Banana(x, y);
    			Blueberry blueberry = new Blueberry(x, y);
    			Basket basket = new Basket(x, y, apple, banana, blueberry);
    			consumables.add(basket);        		
        	}
        }
        

        // Move and draw each enemy
        for (Enemy enemy: this.enemies) {
            enemy.move(player);
            enemy.drawOnScreen(graphics);
        }
        
        //Draw each fruit
        for (Consumable consumable : consumables) {
        	consumable.drawOnScreen(graphics);
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

    // Draw player health points in the top middle
    private void drawPlayerHP (Graphics graphics) {
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

    private class MyKeyAdapter extends KeyAdapter {
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

    // Check player-enemy, enemy-enemy and player-fruit collisions
    private void checkCollisions () {
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

            boolean collided = enemy.checkCollision(
                player.getX(), player.getY(), player.getWidth(), player.getHeight()
            );

            // Check the collision between the player and some enemy
            if (collided && player.getImunityCounter() > 100) {
                player.decrementCurrentHP();

                if (player.getCurrentHP() <= 0) {
                    this.running = false;
                }

                // Penalize the collision and reset the score multiplier
                score -= Math.min(100, score);
                scoreMultiplier = 1;

                player.setImunityCounter(0);
            }
        }
        
        for (Consumable consumable : this.consumables) {
        	if (consumable == null) {
        		continue;
        	}
        	
        	boolean collided = consumable.checkCollision(
                    player.getX(), player.getY(), player.getWidth(), player.getHeight()
        	);
        	
        	if (collided) {
            	//Fortifies the player and removes the fruit
        		consumable.fortify(player);
        		consumables.remove(consumable);
        		break;
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
