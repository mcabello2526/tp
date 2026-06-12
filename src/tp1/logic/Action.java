package tp1.logic;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

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
	
	public static Action parse(String atb) throws ActionParseException {
		//horizontales 
		if (atb.equalsIgnoreCase("R") || atb.equalsIgnoreCase("RIGHT")) return Action.RIGHT; 
		else if (atb.equalsIgnoreCase("L")|| atb.equalsIgnoreCase("LEFT")) return Action.LEFT;
		
		// verticales 
		else if (atb.equalsIgnoreCase("U")|| atb.equalsIgnoreCase("UP")) return Action.UP;
		else if (atb.equalsIgnoreCase("D")|| atb.equalsIgnoreCase("DOWN")) return Action.DOWN;
		
		// stop
		else if (atb.equalsIgnoreCase("S")|| atb.equalsIgnoreCase("STOP")) return Action.STOP;
		
		//default 
		else if (atb.equalsIgnoreCase(" ")) return Action.RIGHT; 
		
		else {

			throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(atb)); 
		}
	}


}


	
	
	
	
	
	
