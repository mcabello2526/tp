package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameWorld;
import tp1.logic.GameModel; 
import tp1.logic.Position;

public abstract class GameObject implements GameItem{ // TODO 

	protected Position pos; // If you can, make it private. No se como cambiarlo a private porque lo uso en MovingObject
	private boolean isAlive;
	protected boolean isSolid; // *** preguntar si esto puede ser protected o si tiene que ser privado
	protected GameWorld game; 
	
// ***** ??? ****** deberiamos cambiar a GameWorld?
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
	
	public boolean isInPosition(Position p) {
		// TODO fill your code here, it should depends on the status of the object
		return isAlive && p!= null && pos.equals(p); 
		// *** a que se refiere con lo del status ******
		
	}
 	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void dead(){
		this.isAlive = false;
	}
	
	// nueva
	public boolean isSolid() {  
	// no tiene que ser abstracto
		return isSolid; 
	}
	
	
	public abstract void update(); 
	// de esta manera los objetos heredan el metodo 
	//y les obliga a que lo implementen 
	
	public abstract String getIcon();
	
// *** este metodo se implementa aquí o en cada clase?****************
/*    public String toString() {
		// para mostrar el string del getIcon()
		return this.getIcon(); 
	}
*/
	

	protected void move(Action dir) {
		Position next = pos.assignPos(dir); 
		if (next.posValida() && !game.isSolid(next)) {
			pos = next; 
		}
	}
	
	@Override 
	public boolean receiveInteraction(Mario obj) {return false;} 
	@Override 
	public boolean receiveInteraction(Goomba g) {return false;}
	@Override
	public boolean receiveInteraction (ExitDoor exitDoor) {return false;}
	@Override 
	public boolean receiveInteraction (Land land) {return false; }	
	@Override
	public boolean receiveInteraction (Mushroom mu) {return false; }
	@Override
	public boolean receiveInteraction (Box box) {return false;}
	
	
	@Override
	public GameObject parse (String objWords[], GameWorld game) {
		
		 GameObject obj = GameObjectFactory.parse(objWords, game);
		 
		 return obj; 
	}
	
// podria ser abstract
}
