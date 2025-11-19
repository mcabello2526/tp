package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameWorld;
import tp1.logic.GameModel; 
import tp1.logic.Position;

public abstract class GameObject implements GameItem { // TODO 

	protected Position pos; 
	 
	private boolean isAlive;
	protected boolean isSolid; 
	protected GameWorld game; 
	

	public GameObject(GameWorld game, Position pos) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
		this.isSolid = false; 
	}
	
	public GameObject() {
		isAlive = true; 
		pos = null; 
		isSolid = false; 
		game = null; 
	}
	
	@Override 
	public boolean isInPosition(Position p) {
		return isAlive && p!= null && pos.equals(p); 	
	}
 	
	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
	public void dead(){
		this.isAlive = false;
	}
	
	@Override
	public boolean isSolid() {  
		return isSolid; 
	}
	
	
	public abstract void update(); 
	// de esta manera los objetos heredan el metodo 
	// y les obliga a que lo implementen 
	
	public abstract String getIcon();
	
   @Override
   public String toString() {
		return this.getIcon(); 
	}

   protected void move(Action dir) {
		Position next = pos.assignPos(dir); 
		if (next.posValida() && !game.isSolid(next) && dir != Action.UP) {
			pos = pos.assignPos(dir);  
		}
		if (dir == Action.UP) {
			pos = pos.assignPos(dir); 
		}
	}
	
	@Override 
	public boolean receiveInteraction(Mario obj) {return false;} 
	@Override 
	public boolean receiveInteraction(Goomba g) {return false;}
	@Override
	public boolean receiveInteraction (ExitDoor exitDoor) {return false;}
	@Override 
	public boolean receiveInteraction (Land land) {return false;}	
	@Override
	public boolean receiveInteraction (Mushroom mu) {return false;}
	@Override
	public boolean receiveInteraction (Box box) {return false;}
	
	
	@Override  // podria ser abstract
	public GameObject parse (String objWords[], GameWorld game) {
		return null; 
	}
	
	
}
