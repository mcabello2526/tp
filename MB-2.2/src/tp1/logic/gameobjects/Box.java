package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.gameobjects.GameObject;
import tp1.view.Messages;
import tp1.logic.Position;

public class Box extends GameObject {
	
	private boolean isEmpty; 
	
	public Box() {
		super();
	}
	
	public Box(GameWorld game, Position pos) {
		super(game, pos); 
		isSolid = true;
		isEmpty = false; 
	}
	
	@Override
	public void update() {
		//
	}
	
	@Override 
	public boolean interactWith(GameItem other) {
		if (other == null) return false; 
		
		boolean canInteract = other.isInPosition(pos); 
		
		if (canInteract) {
			other.receiveInteraction(this); 
		}
		
		return canInteract;  
	}
	
	@Override 
	public String getIcon() {
		if (isEmpty) {
			return Messages.EMPTY_BOX; 
		}
		else {
			return Messages.BOX; 
		}
	}
	
	@Override 
	public boolean receiveInteraction (Mario mr) {
		if (mr == null) return false; 
		if (!isEmpty) {
			isEmpty = true;
			Position arriba = pos.assignPos(Action.UP); 
			if (arriba.posValida()) {
				Mushroom m = new Mushroom (game, arriba); 
				game.addObject(m);
				game.addPoints(50);
			}
			return true ;
		}
		return false; 
	}
	
	@Override 
	public GameObject parse (String[] objWords, GameWorld game) {
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("BOX") && !objWords[1].equalsIgnoreCase("B"))return null; 
		
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null; 
		
		return new Land(game, pos);			
		
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
	

