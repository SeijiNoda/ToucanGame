package directions;

public enum DirectionsEnum {
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	// This method seems quite ugly to me, but couldn't be bothered to search a better reflexive approach
	public static DirectionsEnum flip(DirectionsEnum direction) {
		switch (direction) {
			case NORTH:
				direction = SOUTH;
				break;
			case SOUTH:
				direction = NORTH;
				break;
			case EAST:
				direction = WEST;
				break;
			case WEST:
				direction = EAST;
				break;
		}
		
		return direction;
	}
}
