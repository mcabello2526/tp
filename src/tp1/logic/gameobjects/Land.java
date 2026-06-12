package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.GameWorld;

public class Land extends GameObject{ 
	
	
	public Land() {
		super();
	}
	
	public Land (GameWorld game, Position pos) {
		super (game, pos, true); 

	}
	

	private Land(Land original, GameWorld game) {
	    super(game, original.pos.copy(), true);
	}

	@Override
	public Land copy(GameWorld game) {
	    return new Land(this, game);
	}

//**** PARSE **********************************************************************************************************************	
	@Override 
	public GameObject parse(String[] objWords, GameWorld game) throws PositionParseException, ObjectParseException {
		//comprabamos que sea land 
		if (objWords == null || objWords.length < 2) return null;
		if (!objWords[1].equalsIgnoreCase("LAND") && !objWords[1].equalsIgnoreCase("L")) return null; 
		
		//tiene que ser menor que 2 palabras 
		if (objWords.length > 2) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
		
		Position pos;
		try {
			// parseamos posicion 
			pos = Position.parse(objWords[0]);
		} catch(PositionParseException ppe) {
			throw new PositionParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe); 
		}
		
		return new Land(game, pos);			
	}
	

//**** UPDATE E INTERACCIONES ******************************************************************************************************
	@Override
	public void update() {
	
	}

	@Override 
	public boolean interactWith(GameItem other) {
		return other.receiveInteraction(this); 
	}

	@Override 
	public boolean receiveInteraction(Goomba obj) { return true; }
	
	@Override 
	public boolean receiveInteraction (Mario mario) { return true; }
	
	@Override
	public boolean pushOut (Mario mr) { return isSolid(); }
	
//**** RESPRESENTACION TEXTUAL *******************************************************************************************************
	
	@Override
	public String toString() {
	    return super.toString() + " Land"; 
	}
	
	@Override
	public String getIcon() {
		return Messages.LAND; 
	}
	

}
