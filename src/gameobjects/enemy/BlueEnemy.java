package gameObjects.enemy;

import java.awt.Graphics;

import directions.Directions;
import player.Player;

public class BlueEnemy extends Enemy {

    // Constructor of the class without parameters
    public BlueEnemy () {
    	speed = 2;
        width = 28;
        height = 28;
        this.loadImage("src/images/blueEnemy/0.png", 0);
        this.loadImage("src/images/blueEnemy/1.png", 1);
        this.loadImage("src/images/blueEnemy/2.png", 2);
        this.loadImage("src/images/blueEnemy/3.png", 3);
    }

    // Constructor of the class with parameters
    public BlueEnemy (int x, int y) {
        this.x = x;
        this.y = y;
        
        speed = 2;
        width = 28;
        height = 28;
        this.loadImage("src/images/blueEnemy/0.png", 0);
        this.loadImage("src/images/blueEnemy/1.png", 1);
        this.loadImage("src/images/blueEnemy/2.png", 2);
        this.loadImage("src/images/blueEnemy/3.png", 3);
    }

    // Called to choose the correct frame to draw on screen
    public void drawOnScreen (Graphics graphics) {
        int frame = (this.getFrameCounter() / 12) % 2;

        if (this.getDirection() == Directions.W) {
            frame += 2;
        }

        graphics.drawImage(this.getImage(frame), this.getX(), this.getY(), null);
        this.incrementFrameCounter();
    }

    @Override
    public void move (Player player) {
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
