package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.*;
import tp1.view.Messages;
public class Mushroom extends MovingObject {
	
	public Mushroom() {
		super(); 
	}
	
	public Mushroom(GameWorld game, Position pos) {
		super(game, pos, Action.RIGHT); 
		isSolid = false; 
	}
	
	public Mushroom (GameWorld game, Position pos, Action act) {
		super(game, pos, act); 
		this.isSolid = false; 
	}
	
	@Override 
	public boolean interactWith (GameItem other) {
		if (other == null) return false; 
		
		boolean canInteract = other.isInPosition(pos); 
		
		if (canInteract) {
			other.receiveInteraction(this); 
		}
		
		return canInteract;  
	}
	
	@Override 
	public boolean receiveInteraction (Mario obj) {
		if (obj == null) return false; 
		else 
			dead();
		return true; 
	}
	
	@Override 
	public String getIcon() {
		return Messages.MUSHROOM;
	}
	
	@Override
	public void update() {
		super.automaticMovement();
	}
	
	@Override 
	public GameObject parse (String[] objWords, GameWorld game) throws PositionParseException, ActionParseException{
		
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("MUSHROOM") && !objWords[1].equalsIgnoreCase("MU")) return null; 
		
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null;
		
		Action dir = Action.RIGHT; 
		
		if (objWords.length >= 3){
			dir = parseDir(objWords[2]); 
			/*
			String act = objWords[2].toUpperCase(); 
	        if (act.equals("L") || act.equals("LEFT")) dir = Action.LEFT;
	        else {
	        	throw new ActionParseException(Messages.UNKNOWN_DIRECTION.formatted(dir)); 
	        }
	        */
		}
		
		return new Mushroom (game, pos, dir); 
	}
	
	private Action parseDir (String atb) throws ActionParseException {
		if (atb.equalsIgnoreCase("R") || atb.equalsIgnoreCase("RIGHT")) return Action.RIGHT; 
		else if(atb.equalsIgnoreCase("L")|| atb.equalsIgnoreCase("LEFT")) return Action.LEFT;
		else if (atb.equalsIgnoreCase(" ")) return Action.LEFT; 
		else throw new ActionParseException (Messages.UNKNOWN_DIRECTION.formatted(atb)); 
	}
	
	public static Position parsePos(String pos) throws PositionParseException{
	    if (pos == null) return null;
	    pos = pos.trim();
	    if (!pos.startsWith("(") || !pos.endsWith(")")) return null; 

	    String inner = pos.substring(1, pos.length() - 1); 
	    String[] parts = inner.split(",");
	    if (parts.length != 2) return null;
	    int n = 0; 
	    int m = 0; 
	    
	    	try {
	        n = Integer.parseInt(parts[0].trim());
	    	}catch (NumberFormatException e ) {
	    		throw new PositionParseException (Messages.INVALID_OBJECT_POSITION.formatted(parts[0])); 
	    	}
	    	
	    	try {
	    		m = Integer.parseInt(parts[1].trim());
	    	}catch (NumberFormatException e ) {
	    		throw new PositionParseException (Messages.INVALID_OBJECT_POSITION.formatted(parts[1])); 
	    	}	
	    	
	    	if (m < 0 || m > 29 || n < 0 || n > 14) {
    			//return null;
    			throw new PositionParseException(Messages.OFF_BOARD_POSITION.formatted(pos)); 
    		}
	    	
	    	return new Position(n, m); 
	}
	@Override
	public String toString() {
	    return super.toString() + " MushRoom " + dir; 
	}
	
}
