
public class Blueberry extends Fruit {

	public Blueberry(int x, int y) {
		super(x, y);
		this.loadImage("src/images/fruits/blueberry.png", 0);
	}
	
	public void fortify (Player player) {
		player.setImunityCounter(0);;
	}

}
