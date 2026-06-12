package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;

import tp1.logic.Position;
import tp1.view.Messages;

import tp1.logic.GameWorld; 

public class ExitDoor extends GameObject {
	
	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos, false); 
		//this.isSolid = false; 
	}
	
	public ExitDoor () {
		super(); 
	}
	
	private ExitDoor (ExitDoor original, GameWorld game) {
	    super(game, original.pos.copy(), false);
	}

	@Override
	public ExitDoor copy(GameWorld game) {
	    return new ExitDoor(this, game);
	}

//**** PARSE ******************************************************************************************************************	
	@Override 
	public GameObject parse(String[] objWords, GameWorld game) throws PositionParseException, ObjectParseException {
		// comprobamos si es este el objeto 
		if (objWords == null || objWords.length < 2) return null;
		if (!objWords[1].equalsIgnoreCase("EXITDOOR") && !objWords[1].equalsIgnoreCase("ED")) return null; 
		
		// tiene que ser dos palabras, no mas
		if (objWords.length > 2) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
		
		Position pos;
		try {
			//parseamos posicion 
			pos = Position.parse(objWords[0]);
			
		} catch(PositionParseException ppe) {
			throw new PositionParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe); 
		}
		
		return new ExitDoor(game, pos);			
	}
	
//**** UPDATE E INTERACCIONES **************************************************************************************************
	
	@Override
	public void update() {}
	
	@Override 
	public boolean interactWith(GameItem other) {
	     boolean canInteract = other.isInPosition(this.pos);
	     if (canInteract) {
	          
	          return other.receiveInteraction(this);
	     }
	     return false; 
	}
	
	@Override
	public boolean receiveInteraction(Mario mario) { return true; }
	
//**** VISTA TEXTUAL ************************************************************************************************************
	
	@Override
	public String getIcon() {
		return Messages.EXIT_DOOR; 
	}
	
	@Override
	public String toString() {
	    return super.toString() + " ExitDoor"; 
	}
	


	
}
