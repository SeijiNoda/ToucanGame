
public class Blueberry extends Fruit {

	public Blueberry(int x, int y) {
		super(x, y);
		this.loadImage("src/images/fruits/blueberry.png", 0);
	}
	
	// blueberry's buff gives the player imunity for some seconds
	@Override
	public void fortify (Player player) {
		player.setImunityCounter(0);
	}

}
