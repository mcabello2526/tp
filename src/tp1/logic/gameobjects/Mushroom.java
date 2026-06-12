package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.*;
import tp1.view.Messages;

public class Mushroom extends MovingObject {
	
	public Mushroom() {
		super(); 
	}
	
	public Mushroom(GameWorld game, Position pos) {
		super(game, pos, Action.RIGHT, false); 
	}
	
	public Mushroom (GameWorld game, Position pos, Action act) {
		super(game, pos, act, false); 

	}
	

	private Mushroom(Mushroom original, GameWorld game) {
	    super(game, original.pos.copy(), original.dir, false);
	}
	
	@Override
	public Mushroom copy(GameWorld game) {
	    return new Mushroom(this, game);
	}



//**** PARSE ******************************************************************************************************************************************	
	
	@Override 
	public GameObject parse(String[] objWords, GameWorld game) throws PositionParseException, ActionParseException, ObjectParseException {
		
		if (objWords == null || objWords.length < 2) return null;
		if (!objWords[1].equalsIgnoreCase("MUSHROOM") && !objWords[1].equalsIgnoreCase("M")) return null; 
		
		if (objWords.length > 3) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
		
		Position pos; 
		try {
		
			pos = Position.parse(objWords[0]); 
		} catch(PositionParseException ppe) {
			throw new PositionParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe); 
		}
		
		Action dir = Action.RIGHT; 
		
		if (objWords.length >= 3){
			try {
				dir = Action.parse(objWords[2]);
			} catch (ActionParseException ape){
				
				throw new ActionParseException(Messages.UNKNOWN_DIRECTION.formatted(String.join(" ", objWords)), ape); 
			}
			
		
			if (dir != Action.LEFT && dir != Action.RIGHT && dir != Action.STOP) {
				throw new ObjectParseException(Messages.INVALID_DIRECTION.formatted(String.join(" ", objWords)));
			}
		}
		
		return new Mushroom(game, pos, dir); 
	}
	
//**** UPDATE E INTERACCIONES ***********************************************************************************************************	
	
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
	public void update() {
		super.automaticMovement();
	}
	


//**** RESPRESENTACION TEXTUAL ***********************************************************************************************************
	
	@Override
	public String toString() {
	    return super.toString() + " MushRoom " + dir; 
	}
	
	@Override 
	public String getIcon() {
		return Messages.MUSHROOM;
	}



	
}
