package tp1.logic.gameobjects;

import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.GameModel; 
import tp1.logic.GameWorld; 

public class ExitDoor extends GameObject {
	
	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos); 
		this.isSolid = false; 
	}
	
	public ExitDoor () {
		super(); 
	}
	
	@Override
	public void update() {
		 
	}
	
	@Override
	public String getIcon() {
		return Messages.EXIT_DOOR; 
	}
	
	@Override 
	public boolean interactWith(GameItem other) {
	     boolean canInteract = other.isInPosition(this.pos);
	     if (canInteract) {
	          
	          return other.receiveInteraction(this);
	     }
	     return false; 
	}
	
	@Override
	public boolean receiveInteraction(Mario mario) {
		return true; 
	}
	
	@Override 
	public GameObject parse (String[] objWords, GameWorld game) throws PositionParseException {
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("EXITDOOR") && !objWords[1].equalsIgnoreCase("ED"))return null; 
		
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null; 
		
		return new ExitDoor(game, pos);			
		
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
	    return super.toString() + " ExitDoor"; 
	}
	
}
