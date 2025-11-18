package tp1.logic.gameobjects;

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
	public GameObject parse (String[] objWords, GameWorld game) {
		
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("MUSHROOM") && !objWords[1].equalsIgnoreCase("MU")) return null; 
		
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null;
		
		Action dir = Action.RIGHT; 
		
		if (objWords.length >= 3){
			String act = objWords[2].toUpperCase(); 
	        if (act.equals("L") || act.equals("LEFT")) dir = Action.LEFT;
		}
		
		return new Mushroom (game, pos, dir); 
	}
	
	public static Position parsePos(String pos) {
	    if (pos == null) return null;
	    pos = pos.trim();
	    if (!pos.startsWith("(") || !pos.endsWith(")")) return null; 

	    String inner = pos.substring(1, pos.length() - 1); 
	    String[] parts = inner.split(",");
	    if (parts.length != 2) return null;

	    try {
	        int n = Integer.parseInt(parts[0].trim());
	        int m = Integer.parseInt(parts[1].trim());
	        
	        return new Position(n, m); 
	    } catch (NumberFormatException e) {
	        return null; 
	    }
	}

/*
	public static Position parsePos (String pos) {
		int[]nums = new int[3]; 
		int row = 0; 
		int col = 0; 
		if (pos == null) return null; 
		
		for (int i = 0; i < pos.length(); i++) {
			char c = pos.charAt(0); 
			if (c != '(' && c != ')' && c!= ',') {
				nums[i]= c - '0'; 
			}
		}
		
		if (nums.length == 2) {
			row = nums[0]; 
			col = nums[1]; 
		}
		
		return new Position (col, row); 

	}
*/	
}
