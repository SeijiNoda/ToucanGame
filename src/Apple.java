
public class Apple extends Fruit {
	
	public Apple(int x, int y) {
		super(x, y);
		this.loadImage("/src/images/fruits/apple.png", 0);
	}
	
	public void fortify (Player player) {
		player.incrementCurrentHP();
	}


}
