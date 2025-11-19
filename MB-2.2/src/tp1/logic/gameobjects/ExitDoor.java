package tp1.logic.gameobjects;

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
		// sin implementar porque no se hace update 
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
	public GameObject parse (String[] objWords, GameWorld game) {
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("EXITDOOR") && !objWords[1].equalsIgnoreCase("ED"))return null; 
		
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null; 
		
		return new ExitDoor(game, pos);			
		
	}
	
	
	public static Position parsePos(String pos) {
	    if (pos == null) return null;
	    pos = pos.trim();
	    if (!pos.startsWith("(") || !pos.endsWith(")")) return null; 

	    String inner = pos.substring(1, pos.length() - 1); 
	    String[] parts = inner.split(",");
	    if (parts.length != 2) return null;
	    else {
	        int n = Integer.parseInt(parts[0].trim());
	        int m = Integer.parseInt(parts[1].trim());
	        
    		if (n < 0 || n > 29 || m < 0 || m > 14) {
    			return null;
    		}		
    		else {
    			return new Position(n, m); 
    		}   
	    } 
	}

	
}
