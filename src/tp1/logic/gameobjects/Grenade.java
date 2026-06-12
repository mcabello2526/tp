package tp1.logic.gameobjects;
import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Grenade extends MovingObject{
	private int lives ; 
	
	public Grenade() {
		super(); 
		
	}
	
	public Grenade(GameWorld game, Position pos) {
		super(game, pos, Action.RIGHT, false); 
		this.lives = 3; 
	}
	
	public Grenade (GameWorld game, Position pos, Action act) {
		super(game, pos, act, false); 
		this.lives = 3; 
	}
	
	private Grenade (Grenade original, GameWorld game) {
	    super(game, original.pos.copy(), original.dir, false);
	    this.lives = original.lives; 
	}

	@Override
	public Grenade copy(GameWorld game) {
		return new Grenade (this, game); 
	}

	

	
	//PARSE 
	
	@Override 
	public GameObject parse(String[] objWords, GameWorld game) throws PositionParseException, ActionParseException, ObjectParseException {
		
		if (objWords == null || objWords.length < 2) return null;
		if (!objWords[1].equalsIgnoreCase("GRENADE") && !objWords[1].equalsIgnoreCase("G")) return null; 
		
		if (objWords.length > 3) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
		
		Action dir = Action.RIGHT; 
		
		if (objWords.length >= 3){
			try {
				dir = Action.parse(objWords[2]);
			} catch (ActionParseException ape){
				
				throw new ActionParseException(Messages.UNKNOWN_DIRECTION.formatted(String.join(" ", objWords)), ape); 
			}
			
		
			if (dir != Action.LEFT && dir != Action.RIGHT && dir != Action.STOP) {
				throw new ObjectParseException(Messages.INVALID_DIRECTION.formatted(String.join(" ", objWords)));
			}
		}
		
		return new Mushroom(game, pos, dir); 
	}
	
	//UPDATE E INTERACCIONES
	@Override 
	public boolean interactWith (GameItem other) {
		    
		if (other == null) return false; 
	
		
		boolean canInteract = other.isInPosition(pos); 
		
		if (canInteract) {
			other.receiveInteraction(this); 
		}
		
		return canInteract;  
	}
	
	@Override 
	public boolean receiveInteraction (Mario obj) {
		if (obj == null) return false; 
		else 
			dead();
		return true; 
	}
	
	@Override
	public boolean receiveInteraction (Land obj) {
		if (obj == null) return false; 
		else {
			dead(); 
		}
		return true; 
	}
	
	@Override 
	public boolean receiveInteraction (Goomba obj) {
		if (obj == null) return false; 
		else {
			dead(); 
		}
		return true; 
	}
	
	
	
	@Override
	public void update() {
		if (!isAlive ()) return; 
		
        if (!canMoveTo(this.dir)) {	          
            this.dir = this.dir.opposite(); 
            

            this.act = Action.STOP; 
            
        } else {
            this.act = this.dir;
            move(this.dir);
            game.doInteractionFrom(this);
        }
	}
	
	
	@Override
	public String toString() {
	    return super.toString() + " Goomba " + dir + lives; 
	}
	
	@Override 
	public String getIcon() {
		return Messages.GRENADE;		
	}
	
	


}
