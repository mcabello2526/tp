package tp1.logic;


/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0);
	
	private int x;
	private int y;
	
	private Action(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	public Action opposite() {
		switch(this) {
		case LEFT: return RIGHT; 
		case RIGHT: return LEFT; 
		case UP: return DOWN; 
		case DOWN: return UP; 
		default: return STOP; 
		}
	}

	public boolean isHorizontal() {
		return this == LEFT || this == RIGHT; 
	}
	
	public boolean isVertical() {
		return this == UP || this == DOWN; 
	}
	
}


	
	
	
	
	
	
