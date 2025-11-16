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



	//*** hay que mirar este metodo porque no hace ni de cerca lo que tiene que hacer el movimiento automatico*****//

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
				act = Action.RIGHT; 
				return; 
			}
			else {
				dir = dir.opposite(); 
				act = dir; 
			}
		}
		else if (dir == Action.LEFT && onGround (dir)) {
			Position left = pos.assignPos(dir); 
			if (!left.posValida()) {
				dir = dir.opposite(); 
				act = dir; 
			}
			else if (left.posValida() && game.isSolid(left)) {
				dir = dir.opposite(); 
				act = dir; 
				//move(dir); 
			}
			else {
				move(dir); 
				return; 
			}
		}
		else if (dir == Action.DOWN || dir == Action.DOWN || !onGround(dir)) {
			Position next = pos.assignPos(Action.DOWN);
			dir = Action.DOWN; 
			
			if (next.posValida() && !game.isSolid(next)) {
				isFalling = true; 
				move(dir); 
			}
			else if(next.posValida() && game.isSolid(next)) {
				isFalling = false; 
				move(dir); 
			}
			else if(!next.posValida() && !game.isSolid(next)) {
				dead(); 
				isFalling = false; 
				move(dir); 
			}
			
		}
		else if (onGround(Action.DOWN)){
			dir = Action.STOP; 
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
        Position below = this.pos.assignPos(act); 
        return this.game.isSolid(below) || this.game.isSolid(this.pos);		    	
			       
	}
}
