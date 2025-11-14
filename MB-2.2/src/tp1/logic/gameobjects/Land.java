package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;
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
	
// **** Preguntar si esta bien esto *****************************************************
	@Override 
	public String toString() {
		return this.getIcon(); 
	}
	

	@Override 
	public boolean interactWith(GameItem other) {
		return other.receiveInteraction(this); 
	}


	@Override 
	public boolean receiveInteraction(Goomba obj) {
		return true; 
	};
	
	@Override 
	public boolean receiveInteraction (Mario mario) {  // no le pasa nada al land si algo interactua con ella										   
		return true; 											  // solo se marca que hay algo que ha interactuado con ella
		
	}
	
	@Override 
	public GameObject parse (String[] objWords, GameWorld game) {
		if (objWords == null || objWords.length < 2) return null; 
		Position pos = parsePos(objWords[0]); 
		if (pos != null && (objWords[1].equalsIgnoreCase("LAND") ||objWords[1].equalsIgnoreCase("L"))){
			return new Land(game, pos); 
			}
				
	return null; 	
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
	// *** el toString aqui o en el GameObject? ***
	// *** y si lo ponemos en el GameObject deberia ser abstract
	// y luego implementar en cada clase?***

}
