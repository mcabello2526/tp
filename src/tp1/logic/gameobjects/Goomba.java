package tp1.logic.gameobjects;


import tp1.logic.Position;
import tp1.view.Messages;
import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;

import tp1.logic.GameWorld;

public class Goomba extends MovingObject {
	
	private static final int GOOMBA_PRIORITY = 2; 
	
	public Goomba() {
		super(); 
	}

	public Goomba(GameWorld game, Position pos) {
		super(game, pos, Action.LEFT, false);  
		
	}
	
	public Goomba(GameWorld game, Position pos, Action act) {
		super(game, pos, act, false);  
		 
	}
	
	
	private Goomba(Goomba original, GameWorld game) {
	    super(game, original.pos.copy(), original.dir, false);
	}
	 
	@Override
	public Goomba copy(GameWorld game) {
	    return new Goomba(this, game);
	}

//**** PARSE ***********************************************************************************************************************************
	
	@Override 
	public GameObject parse(String[] objWords, GameWorld game) throws PositionParseException, ActionParseException, ObjectParseException {
		//comprobamos que sea un goomba si no devolvemos null
		if (objWords == null || objWords.length < 2) return null;
		
		if (!objWords[1].equalsIgnoreCase("GOOMBA") && !objWords[1].equalsIgnoreCase("G")) return null; 
		
		
		//no pueden ser mas de 3 palabras 
		if (objWords.length > 3) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
		
		Position pos; 
		try {
			//parseamos posicion 
			pos = Position.parse(objWords[0]); 
			
		} catch(PositionParseException ppe) {

			throw new PositionParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe); 
		}
		
		Action dir = Action.LEFT; 
		
		if (objWords.length >= 3){
			try {
				// parseamos accion 
				
				dir = Action.parse(objWords[2]);
			} catch (ActionParseException ape){
		
				throw new ActionParseException(Messages.UNKNOWN_DIRECTION.formatted(String.join(" ", objWords)), ape); 
			}
			
			// si no es una direccion valida lanzamos excepcion
			if (dir != Action.LEFT && dir != Action.RIGHT && dir != Action.STOP) {
				throw new ObjectParseException(Messages.INVALID_DIRECTION.formatted(String.join(" ", objWords)));
			}
		}
		
		return new Goomba(game, pos, dir); 
	}
	
	
	
//**** UPDATE E INTERACCIONES ******************************************************************************************************************** 
	
	@Override  
	public void update() {
		//llamamos al automatico de MovingObject
		super.automaticMovement();
	}
	
	@Override 
	public boolean interactWith (GameItem other) {
		  
		if (other == null) return false; 
		
		
		boolean canInteract = other.isInPosition(pos); 
		
		if (canInteract) {
			other.receiveInteraction(this); 
		}
		
		return canInteract && isAlive();  
	}
	
	@Override 
	public boolean receiveInteraction (Mario obj) {
	    if (obj == null) return false; 
	    
	    if (this.isAlive()) {
	        dead();
	        game.addPoints(100); 
	    }
	    return true; 
	}
	
	@Override 
	public int getUpdatePriority() {
		return GOOMBA_PRIORITY; 
	}
	
	@Override 
	public boolean receiveInteraction (Grenade obj) {
	    if (obj == null) return false; 
	    
	    if (this.isAlive()) {
	        dead();
	        game.addPoints(100); 
	    }
	    return true; 
	}


//**** RESPRESENTACION TEXTUAL ********************************************************************************************************************	

	@Override
	public String toString() {
	    return super.toString() + " Goomba " + dir; 
	}
	
	@Override 
	public String getIcon() {
		return Messages.GOOMBA;		
	}
	


}
