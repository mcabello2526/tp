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
	private int steps; 
	
	public Grenade() {
		super(); 	
	}
	
	public Grenade(GameWorld game, Position pos) {
		super(game, pos, Action.RIGHT, false); 
		this.lives = 3; 
		this.steps = 0; 
	}
	
	public Grenade (GameWorld game, Position pos, Action act, int lives ) {
		super(game, pos, act, false); 
		this.lives = lives; 
		this.steps = 0; 
	}
	
	private Grenade (Grenade original, GameWorld game) {
	    super(game, original.pos.copy(), original.dir, false);
	    this.lives = original.lives; 
	    this.steps = original.steps; 
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
		
		if (objWords.length > 4) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR + String.join(" ", objWords) + "\"");
		}
		
		Position pos;
		try {
			pos = Position.parse(objWords[0]);
		} catch(PositionParseException ppe) {
			throw new PositionParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe); 
		}
		
		Action dir = Action.RIGHT; 
		if (objWords.length >= 3) {
			try {
				dir = Action.parse(objWords[2]);
			} catch (ActionParseException ape){
				throw new ActionParseException(Messages.UNKNOWN_DIRECTION.formatted(String.join(" ", objWords)), ape); 
			}
			if (dir != Action.LEFT && dir != Action.RIGHT && dir != Action.STOP) {
				throw new ObjectParseException(Messages.INVALID_DIRECTION.formatted(String.join(" ", objWords)));
			}
		}
		
		int parsedLives = 3; 
		if (objWords.length == 4) {
			try {
				parsedLives = Integer.parseInt(objWords[3]);
			} catch (NumberFormatException e) {
				throw new ObjectParseException("Invalid lives for grenade: " + objWords[3]);
			}
		}
		
		return new Grenade(game, pos, dir, parsedLives); 
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
		
		if (steps == 4) {
			this.dir =  this.dir.opposite(); 
			this.act = this.dir; 
			steps = 0; 
		}
		
		Position next = pos.assignPos(this.dir); 
		
		if (next.posValida()) {
			
			this.pos = next; 
			this.act = this.dir; 
			steps++; 
			game.doInteractionFrom(this);
			
		}else {
			dead(); 
		}
		
	}
	
	@Override 
	public int getUpdatePriority() {
		return 1;  
	}
	
	@Override
	public String toString() {
	    return super.toString() + " Goomba " + dir + " " + lives; 
	}
	
	@Override 
	public String getIcon() {
		return Messages.GRENADE;		
	}
	
	


}
