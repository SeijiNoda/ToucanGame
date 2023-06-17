package gameobjects;

import java.awt.Graphics;

public interface Interactable {
	public boolean checkCollision(int x, int y, int width, int height);
	
	public void draw(Graphics g);
}
