package gameobjects.fruit;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

public enum BasketTypesEnum {
	AppleBanana(new ImageIcon("src/images/fruits/apple_banana.png").getImage()),
	AppleBlueberry(new ImageIcon("src/images/fruits/apple_blueberry.png").getImage()),
	BananaBlueberry(new ImageIcon("src/images/fruits/banana_blueberry.png").getImage()),
	FullBasket(new ImageIcon("src/images/fruits/basket.png").getImage());
	
	private final Image image;
	
	BasketTypesEnum(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
	
	public static BasketTypesEnum whichBasket(List<Fruit> basket) {
		int apples = 0, bananas = 0, blueberries = 0;
		for (Fruit fruit: basket) {
			if (fruit instanceof Apple) apples++;
			else if (fruit instanceof Banana) bananas++;
			else blueberries++;	
		}
		
		if (apples > 0) {
			if (bananas == 0) return AppleBlueberry;
			else if (blueberries == 0) return AppleBanana;
			return FullBasket;
		} else return BananaBlueberry;
		
	}
}