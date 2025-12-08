package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameModel; 
import tp1.logic.GameWorld;

public class Land extends GameObject{ 
	
	public Land (GameWorld game, Position pos) {
		super (game, pos); 
		this.isSolid = true; 
	}
	
	public Land() {
		super();
	}
	
	@Override
	public void update() {
		//sin implementar porque no hace nada
	}
	
	@Override
	public String getIcon() {
		return Messages.LAND; 
	}
	
	/*
	@Override 
	public String toString() {
		return this.getIcon(); 
	}
	*/

	@Override 
	public boolean interactWith(GameItem other) {
		return other.receiveInteraction(this); 
	}


	@Override 
	public boolean receiveInteraction(Goomba obj) {
		return true; 
	};
	
	@Override 
	public boolean receiveInteraction (Mario mario) {													// no le pasa nada al land si algo interactua con ella										   
		return true; 											// solo se marca que hay algo que ha interactuado con ella	
	}
	
	@Override 
	public GameObject parse (String[] objWords, GameWorld game) throws PositionParseException {
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("LAND") && !objWords[1].equalsIgnoreCase("L"))return null; 
		
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null; 
		
		return new Land(game, pos);			
		
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
	public boolean pushOut (Mario mr) {
		return isSolid(); 
	}
	
	@Override
	public String toString() {
	    return super.toString() + " Land"; 
	}
}
