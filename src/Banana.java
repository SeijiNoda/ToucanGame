
public class Banana extends Fruit {

	public Banana(int x, int y) {
		super(x, y);
		this.loadImage("/src/images/fruits/banana.png", 0);
	}

	@Override
	public void fortify(Player player) {
		player.setBuffCounter(0);
	}

}
