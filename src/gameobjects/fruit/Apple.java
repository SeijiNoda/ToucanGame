package gameObjects.fruit;

import player.Player;

public class Apple extends Fruit {
	
	public Apple(int x, int y) {
		super(x, y);
		this.loadImage("src/images/fruits/apple.png", 0);
	}

	// apple's buff gives 1 HP to player
	@Override
	public void fortify(Player player) {
		if (player.getCurrentHP() < player.getTotalHP())
			player.incrementCurrentHP();
	}
	
}