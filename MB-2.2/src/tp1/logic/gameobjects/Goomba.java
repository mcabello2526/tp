package tp1.logic.gameobjects;

import tp1.logic.Game; 
import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.Action;
import tp1.logic.GameModel; 
import tp1.logic.GameWorld;

public class Goomba extends MovingObject {
	private boolean doubleIcon; 
	
	public Goomba(GameWorld game, Position pos) {
		super(game, pos, Action.LEFT); 
		this.isSolid = false; 
		this.doubleIcon = false; 
	}
	
	public Goomba(GameWorld game, Position pos, Action act) {
		super(game, pos, act); 
		this.isSolid = false; 
		this.doubleIcon = false; 
	}
	
	
	public Goomba() {
		super(); 
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
			game.addPoints(100); 
		return true; 
	}
	
	@Override 
	public String getIcon() {
		if (doubleIcon) {
			return Messages.GOOMBAS;
		}else {
			return Messages.GOOMBA;
		}	
	}
	
	public void comparePosition(Goomba g) { // para saber si hay alguna posicion igual se llama en el container 
		
		if(this.isInPosition(g.pos)) {
			
			this.doubleIcon = true;
			g.doubleIcon = true;
		}
	}
	
	@Override  //mirar si hace falta el override aqui 
	public void update() {
		super.automaticMovement();
	}
	
	@Override 
	public GameObject parse (String[] objWords, GameWorld game) {
		
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("GOOMBA") && !objWords[1].equalsIgnoreCase("G")) return null; 
		
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null;
		
		Action dir = Action.LEFT; 
		
		if (objWords.length >= 3){
			String act = objWords[2].toUpperCase(); 
	        if (act.equals("R") || act.equals("RIGHT")) dir = Action.RIGHT;
		}
		
		return new Goomba (game, pos, dir); 
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
