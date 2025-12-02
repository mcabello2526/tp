package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.Game; 
import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.ActionList; 
import tp1.logic.GameWorld;

public class Mario extends MovingObject{
	
	private boolean isBig; 
	private boolean inExit;
	
	private ActionList actionListPending; 
	
	public Mario(GameWorld game, Position pos) {
		super(game, pos, Action.RIGHT); 
		this.isBig = true; 
		this.inExit = false;
		actionListPending = new ActionList(); 
	}
	
	public Mario(GameWorld game, Position pos, Action act, boolean isBig) {
		super(game, pos, act); 
		this.isBig = isBig; 
		this.inExit = false;
		actionListPending = new ActionList(); 
	}
	
	
	public Mario() { // los instanciamos solo, sin crear el objeto
		super(); 	 // eso lo hacemos con los parses de cada objeto
	}
	
	public boolean isBig() {
		return isBig; 
	}


	
	@Override
	public boolean isInPosition (Position p) {
		if (!isAlive()) return false; 
        if (p == null) return false;
        if (this.pos.equals(p)) return true;

        if (this.isBig()) {
			Position arriba = this.pos.arriba();
			if(arriba.posValida() && arriba.equals(p)) {
				return true;
        }
        }
        return false;
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
	
	public String toString() {
		return this.getIcon(); 
	}
	
	public void addAction(Action act) {
		actionListPending.add(act);
	}
	
	public boolean manualMovement() {
		if (!this.isAlive()) return false;  
		
		if (act == Action.RIGHT) { 
			Position next = this.pos.assignPos(act); 
			if (!next.posValida()) return false; 
			if (!this.game.isSolid(next)) {
				this.dir = Action.RIGHT; 
				this.act = Action.RIGHT; 
				move(dir); 
				game.doInteractionFrom(this); //N
			}
			else {
				this.dir = this.act.opposite();
				move(dir);
				game.doInteractionFrom(this); //N
			}
				
				
		}
		else if (act == Action.LEFT) { 
			Position next = this.pos.assignPos(act); 
			if (!next.posValida()) return false; 
			if (!this.game.isSolid(next)) {
				this.dir = Action.LEFT;
				this.act = Action.LEFT; 
				move(dir);
				game.doInteractionFrom(this); //N
			}
			else {
				this.dir = this.act.opposite();
				move(dir);
				game.doInteractionFrom(this);
			}
				
				
				
		}
		else if (act == Action.UP) {
			Position next = this.pos.assignPos(act); 
			if (!next.posValida()) return false; 
			else {
				move(act); 
				game.doInteractionFrom(this);
			}
			
		}
		else if (act == Action.DOWN) {
			isFalling = true;  
			
			while(isFalling) {
				Position next = this.pos.assignPos(act); 
				
				if (!this.game.isSolid(next) && next.posValida ()) {
					  
					move(act); 
					game.doInteractionFrom(this);
					Position aux = this.pos.assignPos(act); 
					
					if (this.game.isSolid(aux)) {
						isFalling = false; 
						this.act = Action.DOWN; 
					}
					
				}
				else if (onGround(act)) {
					isFalling = false; 
					this.dir = Action.STOP; 
				}
				else if (!this.game.isSolid(next) && !next.posValida ()) {
					isFalling = false; 
					dead(); 
					
					if (game.moreThanZeroLives()) {
						game.reduceNumLives();
						
						if (game.moreThanZeroLives()) {
							game.reset();
						}
						else {
							game.exit();
							if (this.isBig) {
								move(dir);
								move(dir); 
							}
							else {
								move(dir); 
							}
							 
						}
					}
				}
			}
		}
		else if (act == Action.STOP) {
			this.dir = Action.STOP;
			return false; 
		}
		else if (act == null) {
			return false; 
		}
		
		return true; 
	}
	


	public void update() {
		
		if (!isAlive()) return; 
		
		boolean moved = false; 
		Action lastAct = null; 
		
		while ((act = this.actionListPending.firstActionToDo()) != null)  {
			lastAct = act;
			if (this.manualMovement()) moved = true; 
			
		}
        this.actionListPending.clear();
        
        if (act == null) {
        	act = lastAct; 
        }
        
        if (!moved) {
      	  super.automaticMovement(); 
      	 
        }
      	  
	}
	
	@Override
	public boolean interactWith(GameItem other) {
		if (other == null) return false; 
		if (!other.isAlive()) return false; 
		
		boolean canInteract;
		Position above = pos.assignPos(Action.UP);
		
		if (isBig ) {
			canInteract = other.isInPosition(pos) || other.isInPosition(above); 
		}else {
			canInteract = other.isInPosition(pos); 
		}
		
		if (canInteract) {
			other.receiveInteraction(this);
		
			if (isBig) {
				boolean headTap = isInPosition(above) && act == Action.UP; 
				if (headTap && other.pushOut(this)) {
					move(Action.DOWN); 
				}
			}
			else if (!isBig) {
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
			this.inExit = true; 
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
            if (isBig()) {
            	this.isBig = false;
            	            	
            }
            else {         			
            	game.reduceNumLives();
            	game.reset();
            }
            return true;
        }
	}
	
	@Override 
	public boolean receiveInteraction(Mushroom mu) {
		if (mu == null) return false; 
		if (!isBig()) {
			isBig = true; 
		}
		return true; 
	}
	
	
/*
	@Override 
	public boolean receiveInteraction(Box box) {
		if (box == null) return false;
		Position above = pos.assignPos(Action.UP); 
		
		if (isBig) {
			boolean headTap = isInPosition(above) && act == Action.UP; 
			if (headTap && box.pushOut(this)) {
				move(Action.DOWN); 
				return true; 
			}
		}
		else if (!isBig) {
			boolean headTap = isInPosition(pos) && act == Action.UP; 
			if (headTap && box.pushOut(this)) {
				move(Action.DOWN); 
				return true; 
			}
		}
		return false; 
	}
*/
	
	
/*
	@Override
	public boolean receiveInteraction(Land land) {
		if (land == null) return false;
		
		if (this.isBig) {
			Position above = pos.assignPos(Action.UP); 
			
			if (land.isInPosition(above)) {
				land.receiveInteraction(this); //*
				move(Action.DOWN); //*
				return true; 
			}
			
	}
		else if (!this.isBig) { 
			if (land.isInPosition(this.pos)) {
				land.receiveInteraction(this); //*
				move(Action.DOWN); //*
				return true; 
			}
			
		}
		return false;
	}
*/
	
	@Override 
	public Mario parse (String[] objWords, GameWorld game) {
			
		if (objWords == null || objWords.length < 2) return null;
			
		if (!objWords[1].equalsIgnoreCase("MARIO") && !objWords[1].equalsIgnoreCase("M")) return null; 
			
		Position pos = parsePos(objWords[0]); 
		if (pos == null) return null;
			
		Action dir = Action.RIGHT; 
		boolean isBig = true; 
		
		String atb1 = null; 
		if (objWords.length > 2 && objWords[2] != null) {
			atb1 = objWords[2].toUpperCase(); 
		}
		
		String atb2 = null;
		if (objWords.length > 3 && objWords[3] != null) {
			atb2 = objWords[3].toUpperCase(); 
		}
			

		if (atb1 != null) {
			Action parseDir = parseDir(atb1); 
			boolean size = parseSize(atb1); 
			
			if (parseDir != null) {
				dir = parseDir; 
			}
			else if (size || !size) {
				isBig = size; 
			}
		}
		
		if (atb2 != null) {
			Action parseDir = parseDir (atb2); 
			boolean size = parseSize (atb2); 
			
			if (parseDir != null) {
				dir = parseDir; 
			}
			else if (size || !size) {
				isBig = size; 
			}
		}
		return new Mario(game, pos, dir, isBig); 
    }
	
	private Boolean parseSize(String atb) {
		atb = atb.toUpperCase(); 
		if (atb.equalsIgnoreCase("B") || atb.equalsIgnoreCase("BIG")) return true;
		if (atb.equalsIgnoreCase("S") || atb.equalsIgnoreCase(("SMALL")))return false; 
		if (atb.equalsIgnoreCase(" ")) return true; 
		
		return false; 
	}
	
	private Action parseDir (String atb) {
		if (atb.equalsIgnoreCase("R") || atb.equalsIgnoreCase("RIGHT")) return Action.RIGHT; 
		else if(atb.equalsIgnoreCase("L")|| atb.equalsIgnoreCase("LEFT")) return Action.LEFT;
		else if (atb.equalsIgnoreCase(" ")) return Action.RIGHT; 
		return null; 
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
	        
    		if (m < 0 || m > 29 || n < 0 || n > 14) {
    			return null;
    		}		
    		else {
    			return new Position(n, m); 
    		}   
	    } 
	}


}
