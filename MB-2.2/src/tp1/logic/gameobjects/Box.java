package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.gameobjects.GameObject;
import tp1.view.Messages;
import tp1.logic.Position;

public class Box extends GameObject {
	
	private boolean isEmpty; 
	
	public Box() {
		super();
	}
	
	public Box(GameWorld game, Position pos) {
		super(game, pos); 
		isSolid = true;
		isEmpty = false; 
	}
	
	@Override
	public void update() {
		//
	}
	
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
	public String getIcon() {
		if (isEmpty) {
			return Messages.EMPTY_BOX; 
		}
		else {
			return Messages.BOX; 
		}
	}
	
	@Override 
	public boolean receiveInteraction (Mario obj) {
		if (obj == null) return false; 
		if (!isEmpty) {
			isEmpty = true;
			Position arriba = pos.assignPos(Action.UP); 
			if (pos.posValida()) {
				Mushroom m = new Mushroom (game, arriba); 
				game.addObject(m);
				game.addPoints(50);
			}
			return true ;
		}
		return false; 
	}
	
}
