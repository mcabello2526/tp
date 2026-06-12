package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;

import tp1.view.Messages;


public class Box extends GameObject {
	
	private boolean isEmpty; 

	public Box() {
		super();
	}
	
	public Box(GameWorld game, Position pos) {
		super(game, pos, true); 
		isEmpty = false; 
	}
	
	public Box(GameWorld game, Position pos, boolean isEmpty) {
	    super(game, pos, true); 
	    this.isEmpty = isEmpty; 
	}


	
	private Box(Box original, GameWorld game) {
	    super(game, original.pos.copy(), true);
	    this.isEmpty = original.isEmpty; 
	}
	 
	@Override
	public Box copy(GameWorld game) {
	    return new Box(this, game);
	}

	
//**** PARSE **************************************************************************************************************************	
	
	@Override 
	public GameObject parse(String[] objWords, GameWorld game) throws PositionParseException, ObjectParseException {
		// comprobacion de que es este el objeto que se intenta parsear 
	    if (objWords == null || objWords.length < 2) return null;
	    if (!objWords[1].equalsIgnoreCase("BOX") && !objWords[1].equalsIgnoreCase("B")) return null; 
	    
	    // 3 palabras como maximo 
	    if (objWords.length > 3) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
	    
	    Position pos;
		try {
			
			//parse de la posicion
			pos = Position.parse(objWords[0]);
			
		} catch(PositionParseException ppe) {
			throw new PositionParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe); 
		}
	    
		boolean empty = false; 
	    if (objWords.length > 2) {
	    	// vemos si es empty o full la box que intentamos crear
	        String state = objWords[2].toUpperCase();
	        
	        if (state.equals("EMPTY") || state.equals("E")) {
	            empty = true;	           
	        } else if (state.equals("FULL") || state.equals("F")) {
	            empty = false;
	            
	        } else {
	          
	        	throw new ObjectParseException(Messages.INVALID_BOX_STATUS + String.join(" ", objWords) + "\"");
	        }
	    }
	    
	    return new Box(game, pos, empty);			
	}
	
//**** UPDATE E INTERACCIONES ****************************************************************************************************	
	
	@Override
	public void update() {}
	
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
	public boolean receiveInteraction (Mario mr) {
		
		if (mr == null) return false; 
		
		if (!isEmpty) {
			isEmpty = true;
			Position arriba = pos.assignPos(Action.UP); 
			
			if (arriba.posValida()) {
				// creamos una seta 
				Mushroom m = new Mushroom (game, arriba); 
				game.addObject(m);
				game.addPoints(50);
			}
			return true ;
		}
		return false; 
	}
	

	@Override
	public boolean pushOut (Mario mr) {
		return isSolid(); 
	}


//**** VISTA TEXTUAL ***************************************************************************************************************
	
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
	public String toString() {
	    return super.toString() + " Box "; 
	}



} 
	

