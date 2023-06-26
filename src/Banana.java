
public class Banana extends Fruit {

	public Banana(int x, int y) {
		super(x, y);
		this.loadImage("src/images/fruits/banana.png", 0);
	}
	
	// banana's buff makes player's speed increase for some seconds
	@Override
	public void fortify(Player player) {
		player.setBuffCounter(0);
	}

}
