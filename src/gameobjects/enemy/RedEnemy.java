package gameObjects.enemy;

import java.awt.Graphics;
import java.util.Random;
import directions.Directions;
import player.Player;


public class RedEnemy extends Enemy {

    // Random factor to do the enemy following path slightly different to the others
    private final float alpha;

    // Constructor of the class without parameters
    public RedEnemy () {
    	speed = 1;
        width = 28;
        height = 28;

        Random generator = new Random();
        this.alpha = 1 + 1.2f * (generator.nextFloat() - 0.5f);  // Random value between 0.4 and 1.6

        this.loadImage("src/images/redEnemy/0.png", 0);
        this.loadImage("src/images/redEnemy/1.png", 1);
        this.loadImage("src/images/redEnemy/2.png", 2);
        this.loadImage("src/images/redEnemy/3.png", 3);
    }

    // Constructor of the class with parameters
    public RedEnemy (int x, int y) {
        this.x = x;
        this.y = y;

        speed = 1;
        width = 28;
        height = 28;
        
        Random generator = new Random();
        this.alpha = 1 + 1.2f * (generator.nextFloat() - 0.5f);   // Random value between 0.4 and 1.6

        this.loadImage("src/images/redEnemy/0.png", 0);
        this.loadImage("src/images/redEnemy/1.png", 1);
        this.loadImage("src/images/redEnemy/2.png", 2);
        this.loadImage("src/images/redEnemy/3.png", 3);
    }

    // Called to choose the correct frame to draw on screen
    public void drawOnScreen (Graphics graphics) {
        int frame = (this.getFrameCounter() / 12) % 2;

        graphics.drawImage(this.getImage(frame), this.getX(), this.getY(), null);
        this.incrementFrameCounter();
    }

    @Override
    public void move (Player player) {
        int dx = this.getX() - player.getX();   // Displacement in x coordinate
        int dy = this.getY() - player.getY();   // Displacement in y coordinate

        // Compute the best direction (considering the alpha factor) to move to the player
        if (Math.abs(dx) + Math.abs(dy) == 0) {
            this.setDirection(Directions.STOPPED);
        } else if (Math.abs(dx) < Math.abs(dy) * this.alpha) {
            this.setDirection(dy > 0 ? Directions.N : Directions.S);
        } else {
            this.setDirection(dx > 0 ? Directions.W : Directions.E);
        }

        switch (this.getDirection()) {
            case N:   // Move to north
                this.setY(this.getY() - this.getSpeed());
                break;
            case S:   // Move to south
                this.setY(this.getY() + this.getSpeed());
                break;
            case E:   // Move to east
                this.setX(this.getX() + this.getSpeed());
                break;
            case W:   // Move to west
                this.setX(this.getX() - this.getSpeed());
                break;
            case STOPPED:
                break;
        }

        // Guarantees that the enemy always stays
        // inside the game window in x coordinate
        if (this.getX() < 0) {
            this.setX(0);
        } else if (this.getX() > 736) {
            this.setX(736);
        }

        // Guarantees that the enemy always stays
        // inside the game window in y coordinate
        if (this.getY() < 0) {
            this.setY(0);
        } else if (this.getY() > 736) {
            this.setY(736);
        }

        // Enemy hit the game border, so flip the movement direction
        if (this.getX() <= 0 || this.getX() >= 736 || this.getY() <= 0 || this.getY() >= 736) {
            this.setDirection(Directions.flip(this.getDirection()));
        }
    }
}
