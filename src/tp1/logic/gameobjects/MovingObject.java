package tp1.logic.gameobjects;

import tp1.logic.Game; 
import tp1.logic.Position;
import tp1.logic.Action;
import tp1.logic.GameWorld; 
public class MovingObject extends GameObject{
	
	protected Action dir; 
	protected Action act; 
	protected boolean isFalling; 
	
	public MovingObject (GameWorld game, Position pos, Action dir) {
		super (game, pos);
		this.dir = dir; 
		this.isFalling = false; 
		this.act = dir; 
		
	}
	
	public MovingObject() {
		super(); 
	}


	protected void automaticMovement() {
		if (isAlive()) {
		if (dir == Action.RIGHT && onGround(dir) ) {
			Position right = pos.assignPos(dir); 
			
			if (!right.posValida()) {
				dir = dir.opposite(); 
				act = dir; 
			}
			if (!game.isSolid(right)) {
				move(dir);
				game.doInteractionFrom(this);
				act = Action.RIGHT; 
				return; 
			}
			else {
				dir = dir.opposite(); 
				act = dir; 
			}
		}
		else if (dir == Action.LEFT && onGround(act)) {
			Position left = pos.assignPos(dir); 
			if (!left.posValida()) {
				dir = dir.opposite(); 
				act = dir; 
			}
			else if (left.posValida() && game.isSolid(left)) {
				dir = dir.opposite(); 
				act = dir; 
				
			}
			else {
				move(dir); 
				game.doInteractionFrom(this);
				act = dir; 
				return; 
			}
		}
		else if (dir == Action.DOWN || dir == Action.UP || !onGround(dir)) {
			Position next = pos.assignPos(Action.DOWN);
			act = Action.DOWN; 
			
			if (next.posValida() && !game.isSolid(next)) {
				isFalling = true; 
				move(act); 
				game.doInteractionFrom(this);
			}
			else if(next.posValida() && game.isSolid(next)) {
				isFalling = false; 
				move(act); 
				
			}
			else if(!next.posValida() && !game.isSolid(next)) {
				dead(); 
				isFalling = false; 
				move(act); 
				
			}
			
		}
		else if (onGround(Action.DOWN)){
			dir = Action.STOP; 
			act = dir; 
		}
		
	}
}

	@Override
	public void update() {
		if (!isAlive()) return; 
		automaticMovement(); 
	}
	
	@Override 
	public String getIcon() {
		return this.getIcon(); 
	}

	@Override 
	public boolean interactWith(GameItem game) {
		
		return false; 
	}
	
	protected boolean onGround(Action act) {
	  	act = Action.DOWN; 
        Position below = this.pos.assignPos(Action.DOWN); 
        return this.game.isSolid(below) || this.game.isSolid(this.pos);		    	
			       
	}
}
