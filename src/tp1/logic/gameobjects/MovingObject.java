package tp1.logic.gameobjects;


import tp1.logic.Position;
import tp1.logic.Action;
import tp1.logic.GameWorld; 
public abstract class MovingObject extends GameObject{
	
	protected Action dir; 
	protected Action act; 
	protected boolean isFalling; 
	
	public MovingObject() {
		super(); 
	}
	
	public MovingObject (GameWorld game, Position pos, Action dir, boolean isSolid) {
		super (game, pos, isSolid);
		this.dir = dir; 
		this.isFalling = false; 
		this.act = dir; 
		
	}
	
	
//**** UPDATE E INTERACCIONES *********************************************************
	protected boolean canMoveTo(Action direction) {
		if (direction == Action.STOP) {
	        return true;
	    }
	    Position nextBase = this.pos.assignPos(direction);
	    return nextBase.posValida() && !this.game.isSolid(nextBase);
	}

	protected void automaticMovement() {
	    if (!isAlive()) return; 
	    if (!onGround()) {
	        Position next = pos.assignPos(Action.DOWN); 
	        if (next.posValida()) {
	            this.act = Action.DOWN; 
	            move(Action.DOWN); 
	            game.doInteractionFrom(this);
	        } else {
	            dead(); 
	        }
	    } else {
	        if (!canMoveTo(this.dir)) {	          
	            this.dir = this.dir.opposite(); 
	            

	            this.act = Action.STOP; 
	            
	        } else {
	            this.act = this.dir;
	            move(this.dir);
	            game.doInteractionFrom(this);
	        }
	    }
	}

	@Override
	public void update() {
		if (!isAlive()) return; 
		automaticMovement(); 
	}
	
	@Override 
	public boolean interactWith(GameItem game) {
		
		return false; 
	}
	
	protected boolean onGround() {
	    Position below = this.pos.assignPos(Action.DOWN);
	    if (!below.posValida()) return false; 
	    return this.game.isSolid(below);
	}


//**** RESPRESENTACION TEXTUAL ****************************************************
	
	@Override 
	public String getIcon() {
		return this.getIcon(); 
	}

	
	public abstract GameObject copy(GameWorld game); 

	

}
