package player;

import java.awt.Image;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import directions.Directions;

public class Player {
    private int x;
    private int y;

    private int speed;
    private int width;
    private int height;

    private int totalHP;
    private int currentHP;

    // Imunity after collision with enemy
    private int imunityCounter;

    private Image[] images;
    private int frameCounter;

    private Directions direction;
    private Directions movementSide;

    private int buffCounter;

    // Constructor of the class without parameters
    public Player () {
    	speed = 4;
        width = 28;
        height = 28;

        this.totalHP = 3;
        this.currentHP = 3;
        
        imunityCounter = 100;
        
        images = new Image[5];
        frameCounter = 0;
        
        direction = Directions.STOPPED;
        movementSide = Directions.E;
        
        buffCounter = 100;
        
        this.loadImage("src/images/player/0.png", 0);
        this.loadImage("src/images/player/1.png", 1);
        this.loadImage("src/images/player/2.png", 2);
        this.loadImage("src/images/player/3.png", 3);
        this.loadImage("src/images/player/4.png", 4);
    }

    // Constructor of the class with parameters
    public Player (int x, int y) {
    	this.x = x;
    	this.y = y;

    	speed = 4;
        width = 28;
        height = 28;

        this.totalHP = 3;
        this.currentHP = 3;
        
        imunityCounter = 100;
        
        images = new Image[5];
        frameCounter = 0;
        
        direction = Directions.STOPPED;
        movementSide = Directions.E;
        
        buffCounter = 100;

        this.loadImage("src/images/player/0.png", 0);
        this.loadImage("src/images/player/1.png", 1);
        this.loadImage("src/images/player/2.png", 2);
        this.loadImage("src/images/player/3.png", 3);
        this.loadImage("src/images/player/4.png", 4);
    }

    // Pos x getter
    public int getX () {
        return this.x;
    }

    // Pos x setter
    public void setX (int x) {
        this.x = x;
    }

    // Pos y getter
    public int getY () {
        return this.y;
    }

    // Pos y setter
    public void setY (int y) {
        this.y = y;
    }

    // Speed getter
    public int getSpeed () {
        return (this.buffCounter < 100) ? this.speed + 6 : this.speed;
    }

    // Speed setter
    public void setSpeed (int speed) {
        this.speed = speed;
    }

    // Width getter
    public int getWidth () {
        return this.width;
    }

    // Width setter
    public void setWidth (int width) {
        this.width = width;
    }

    // Height getter
    public int getHeight () {
        return this.height;
    }

    // Height setter
    public void setHeight (int height) {
        this.height = height;
    }

    // Total HP getter
    public int getTotalHP () {
        return this.totalHP;
    }

    // Total HP setter
    public void setTotalHP (int totalHP) {
        this.totalHP = totalHP;
    }

    // Current HP getter
    public int getCurrentHP () {
        return this.currentHP;
    }

    // Decrement current HP in one unit
    public void decrementCurrentHP () {
        this.currentHP -= 1;
    }
    
    //Decrement current HP in x units
    public void decrementCurrentHP (int x) {
    	this.currentHP -= x;
    }
    
    //Increment current HP in one unit
    public void incrementCurrentHP () {
    	this.currentHP += 1;
    }
    
    //Increment current HP in x units
    public void incrementCurrentHP (int x) {
    	this.currentHP += x;
    }

    // Imunity counter getter
    public int getImunityCounter () {
        return this.imunityCounter;
    }

    // Imunity counter setter
    public void setImunityCounter (int imunityCounter) {
        this.imunityCounter = imunityCounter;
    }

    // Frame counter getter
    public int getFrameCounter () {
        return this.frameCounter;
    }

    // Frame counter increment
    public void incrementFrameCounter () {
        this.frameCounter += 1;
    }

    // Direction getter
    public Directions getDirection () {
        return this.direction;
    }

    // Direction setter
    public void setDirection (Directions direction) {
        this.direction = direction;
    }

    // Movement side getter
    public Directions getMovementSide () {
        return this.movementSide;
    }

    // Movement side setter
    public void setMovementSide (Directions side) {
        this.movementSide = side;
    }

    // Buffed getter
    public int getBuffCounter () {
        return this.buffCounter;
    }

    // Buffed setter
    public void setBuffCounter (int buffCounter) {
        this.buffCounter = buffCounter;
    }

    // Specific image getter
    public Image getImage (int index) {
        return this.images[index];
    }

    // Load the image and pass it to the desired index on the image vector
    public void loadImage (String filename, int index) {
        ImageIcon imageIcon;

        imageIcon = new ImageIcon(filename);
        this.images[index] = imageIcon.getImage();
    }

    // Called to choose the correct frame to draw on screen
    public void drawOnScreen (Graphics graphics) {
        int frame = (this.getFrameCounter() / 12) % 2;

        if (this.getMovementSide() == Directions.W) {
            frame += 2;
        }

        this.imunityCounter += 1;
        if (this.imunityCounter < 100 && (this.imunityCounter / 8) % 2 == 0) {
            frame = 4;
        }
        
        if (buffCounter < 100) {
            this.buffCounter += 1;
        }

        graphics.drawImage(this.getImage(frame), this.getX(), this.getY(), null);
        this.incrementFrameCounter();
    }

    public void move () {
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

        // Guarantees that the player always stays
        // inside the game window in x coordinate
        if (this.getX() < 0) {
            this.setX(0);
        } else if (this.getX() > 736) {
            this.setX(736);
        }

        // Guarantees that the player always stays
        // inside the game window in y coordinate
        if (this.getY() < 0) {
            this.setY(0);
        } else if (this.getY() > 736) {
            this.setY(736);
        }
    }
}
