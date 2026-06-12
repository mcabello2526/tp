package tp1.logic.gameobjects;

import tp1.logic.Action;

import tp1.logic.GameWorld;

import tp1.logic.Position;
import tp1.exceptions.*;
public abstract class GameObject implements GameItem {

	private static final int DEFAULT_PRIORITY = 3; 
	
	protected Position pos; 
	 
	private boolean isAlive;
	//protected boolean isSolid;
	private final boolean isSolid; 
	
	protected GameWorld game; 
	

	public GameObject(GameWorld game, Position pos, boolean isSolid) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
		//this.isSolid = false; 
		this.isSolid = isSolid; 
	}
	
	public GameObject() {
		isAlive = true; 
		pos = null; 
		isSolid = false; 
		game = null; 
	}


//**** COMPROBACIONES **********************************************************************************************************
	
	@Override 
	public boolean isInPosition(Position p) {
		return isAlive && p!= null && pos.equals(p); 	
	}
	

	public boolean isInBoard() {
		return isAlive && this.pos.posValida(); 	
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
		return this.isSolid; 
	}
	
//**** UPDATE E INTERACCIONES ***************************************************************************************************	
	public abstract GameObject copy(GameWorld game); // copias de los objetos 
	
	public abstract void update(); // update de los objetos 

	protected void move(Action dir) {
		
	   	if (dir == Action.STOP) return;
	   	
		if (dir != Action.UP) {
			Position next = pos.assignPos(dir);
			if (next.posValida() && !game.isSolid(next))
			pos = pos.assignPos(dir);  
		}
		else if (dir == Action.UP) {
			pos = pos.assignPos(dir); 
		}

	}
	
	@Override 
	public boolean pushOut (Mario mr) {return false;}
	
	
	@Override  
	public GameObject parse (String objWords[], GameWorld game) throws GameParseException, OffBoardException{ return null; }
	
	
	public int getUpdatePriority() {
		return DEFAULT_PRIORITY; 
	}
	
	public void performUserAction(Action a) {}
	
	
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
	@Override 
	public boolean receiveInteraction (Grenade grenade) {return false; }
	
	
	
//**** VISTA TEXTUAL ************************************************************************************************************
	public abstract String getIcon();
	
	@Override
	public String toString() {
		return pos.toString();
	}

	
	
}
