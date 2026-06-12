package tp1.logic.gameobjects;

import tp1.logic.Action;

import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.ActionList; 
import tp1.logic.GameWorld;

import tp1.exceptions.*;

public class Mario extends MovingObject{
	
	private static final int MARIO_PRIORITY = 1; 
	
	private boolean big; 

	private Position posArriba; 
	
	private final ActionList actionListPending; 
 
	
	private Mario(Mario original, GameWorld game) {
		//llamamos al super con una copia de mario 
		super(game, original.pos.copy(), original.dir, false);
	    this.big = original.big;
	    
	    this.actionListPending = new ActionList();
	}
	 
	@Override
	public Mario copy(GameWorld game) {
	    return new Mario(this, game);
	}
	
	public Mario() { 
		super(); 	 
		this.actionListPending = new ActionList(); 
	} 
		
	public Mario(GameWorld game, Position pos) {
		super(game, pos, Action.RIGHT, false); 
		this.big = true; 

		this.posArriba = null; 
		actionListPending = new ActionList(); 
	}
	
	public Mario(GameWorld game, Position pos, Action act, boolean isBig) {
		super(game, pos, act, false); 
		this.big = isBig; 
		this.posArriba = null; 
		actionListPending = new ActionList(); 
	}
	
	private boolean isBig() {
		return big; 
	}


	@Override
	public boolean isInPosition (Position p) {
		// si no esta muerto o la posicion es nula o no es la misma pos
		if (!isAlive()) return false; 
        if (p == null) return false;
        if (this.pos.equals(p)) return true;

        // si es grande cogemos la posicion de arriba 
        if (this.big) {
			this.posArriba = this.pos.arriba();
			if(posArriba.posValida() && posArriba.equals(p)) {
				return true;
        }
        }
        return false;
	}

	@Override
	public void dead() {
	    if (!isAlive()) return; 
	    super.dead(); 
	    
	    game.reduceNumLives();
	    if (!game.isFinished()) {
	        game.reset();
	    }
	}
	
	//solo para cuando hagamos un nuevo mario con addObject 
	public void deathByReplacement() {
		super.dead(); 
	}
	
	
	

	
//**** PARSE ******************************************************************************************************************
	
	@Override 
	public Mario parse(String[] objWords, GameWorld game) throws GameParseException, OffBoardException {
		//comprobamos que estamos haciendo parse de mario 
		if (objWords == null || objWords.length < 2) return null;
		if (!objWords[1].equalsIgnoreCase("MARIO") && !objWords[1].equalsIgnoreCase("M")) return null; 
		
		//solo pueden ser menos de 4 palabras 
		if (objWords.length > 4) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
		
		Position pos; 
		try {
			//parse posicion 
			pos = Position.parse(objWords[0]); 
		} catch(PositionParseException ppe) {
			throw new PositionParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe); 
		}
		
		//por default es grande y mira a la derecha 
		Action dir = Action.RIGHT; 
		boolean isBig = true; 

		
		if (objWords.length > 2 && objWords[2] != null) {
			try {
				//parseamos acciones  
				dir = Action.parse(objWords[2]); 
			} catch (ActionParseException ape) {
				
				throw new ActionParseException(Messages.UNKNOWN_DIRECTION.formatted(String.join(" ", objWords)), ape);
			}
			
			// si no es una accion del enumerado lanzamos excepcion 
			if (dir != Action.LEFT && dir != Action.RIGHT && dir != Action.STOP) {
				throw new ObjectParseException(Messages.INVALID_DIRECTION.formatted(String.join(" ", objWords)));
			}
		}
		
		// vemos si queremos que mario sea grande o pequeño 
		if (objWords.length > 3 && objWords[3] != null) {
			String atb2 = objWords[3].toUpperCase();
			if (atb2.equalsIgnoreCase("B") || atb2.equalsIgnoreCase("BIG")) {
				isBig = true;
			} else if (atb2.equalsIgnoreCase("S") || atb2.equalsIgnoreCase("SMALL")) {
				isBig = false;
			} else {
				throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", objWords)));
			}
		}

		//creamos la instancia de mario 
		Mario newMario = new Mario(game, pos, dir, isBig); 
        
        // si esta fuera del tablero lanzamos excepcion 
        if (!newMario.isInBoard()) {
            throw new OffBoardException(Messages.OFF_BOARD_POSITION.formatted(String.join(" ", objWords)));
        }

        return newMario;
    }


//**** UPDATE E INTERACCIONES ********************************************************************************************************


	
	@Override
	protected boolean canMoveTo(Action direction) {
		
		//si es hacia arriba comprobamos la casilla de arriba,
		// y si es grande la siguiente de arriba también
		
	    if (direction == Action.UP) {
	        Position nextPos = this.pos.assignPos(Action.UP);
	        if (this.isBig()) {
	            nextPos = nextPos.assignPos(Action.UP);
	        }
	        return nextPos.posValida(); 
	    }


	    if (!super.canMoveTo(direction)) return false;

	    if (this.isBig() && (direction == Action.LEFT || direction == Action.RIGHT)) {
	        Position nextHead = this.pos.assignPos(direction).assignPos(Action.UP);
	        if (!nextHead.posValida() || this.game.isSolid(nextHead)) {
	            return false;
	        }
	    }
	    return true;
	}
		
	public boolean manualMovement(Action act) {
	    if (!this.isAlive()) return false;  

	   
	    this.act = act; 

	    if (act == Action.RIGHT || act == Action.LEFT) { 
	        this.dir = act; 
	        if (canMoveTo(act)) {
	            move(act); 
	            game.doInteractionFrom(this); 
	        } else {
	            this.dir = this.dir.opposite();
	        }
	        return true;
	    }
	    else if (act == Action.UP) {
	        if (canMoveTo(act)) {
	            move(Action.UP); 
	            game.doInteractionFrom(this);
	        }
	        return true;
	    } 
	    else if (act == Action.DOWN) {
	        if (onGround()) {
	            this.dir = Action.STOP;
	            this.act = Action.STOP; 
	        } else {
	            while (!onGround() && isAlive()) {
	                Position next = pos.assignPos(Action.DOWN); 
	                if (next.posValida()) {
	                    this.act = Action.DOWN;
	                    move(Action.DOWN); 
	                    game.doInteractionFrom(this);
	                } else {
	                    dead();
	                    break;
	                }
	            }
	        }
	        return true; 
	    } 
	    else if (act == Action.STOP) {
	        this.dir = Action.STOP; 
	        return true; 
	    }
	    
	    return false; 
	}
	
	@Override
	public void update() {
	    if (!isAlive()) return; 
	    
	    boolean moved = false; 
	    Action accionActual;
	    
	    
	    while ((accionActual = this.actionListPending.firstActionToDo()) != null)  {
	        if (this.manualMovement(accionActual)) {
	            moved = true; 
	        }
	    }
	    this.actionListPending.clear();
	    
	    if (!moved) {
	        super.automaticMovement(); 
	    }
	}
	
	@Override
	public boolean interactWith(GameItem other) {
		if (!this.isAlive()) return false;
		
		if (other == null) return false; 
		if (!other.isAlive()) return false; 
		
		boolean canInteract;
		Position above = pos.assignPos(Action.UP);
		
		if (big ) {
			canInteract = other.isInPosition(pos) || other.isInPosition(above); 
		}else {
			canInteract = other.isInPosition(pos); 
		}
		
		if (canInteract) {
			other.receiveInteraction(this);
		
			if (big) {
				boolean headTap = isInPosition(above) && act == Action.UP; 
				if (headTap && other.pushOut(this)) {
					move(Action.DOWN); 
				}
			}
			else if (!big) {
				boolean headTap = isInPosition(pos) && act == Action.UP; 
				if (headTap && other.pushOut(this)) {
					move(Action.DOWN); 
				}
				
			}
		}	 

		return canInteract && isAlive(); 
}
	
	@Override
	public boolean receiveInteraction (ExitDoor exitDoor) {
		if (exitDoor == null) return false; 
		else {
			game.exit();  
			game.marioExited(); 
		}
		return true; 
	}

	@Override 
	public boolean receiveInteraction (Goomba obj) {
	    if (obj == null) return false; 
	    
	    if (this.act == Action.DOWN) {
	        return true; 
	    }
	    else {
	        obj.receiveInteraction(this);

	        if (isBig()) {
	            this.big = false;
	        }
	        else {  
	        	this.dead();
	        }
	        return true;
	    }
	}
	
	@Override 
	public boolean receiveInteraction(Mushroom mu) {
		if (mu == null) return false; 
		if (!isBig()) {
			big = true; 
		}
		return true; 
	}
	
	@Override 
	public boolean receiveInteraction (Grenade obj) {
	    if (obj == null) return false; 
	    
	    if (this.isAlive()) {
	        game.reduceNumLives();
	    }
	    return true; 
	}
	
	@Override 
	public int getUpdatePriority() {
		return MARIO_PRIORITY; 
	}
	
	@Override 
	public void performUserAction(Action a) {
		actionListPending.add(a);
	}
	
	public void addAction(Action act) {
		actionListPending.add(act);
	}
	

//**** REPRESENTACION TEXTUAL **********************************************************************************************	
	
	@Override
	public String toString() {
		String size ;
		if (big) {
			size = "big" ;
		}
		else {
			size = "small"; 
		}
	    return super.toString() + " Mario " + dir + " "+ size; 
	}
	
	@Override
	public String getIcon() {   
		
		if (this.dir == Action.STOP) {
			return Messages.MARIO_STOP; 
		}
		if (this.dir == Action.LEFT) {
			return Messages.MARIO_LEFT; 
		}
			
		
		return Messages.MARIO_RIGHT;
		
	}


	



}
