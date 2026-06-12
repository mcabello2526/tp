package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position; 
import tp1.exceptions.*;
public interface GameItem {
	 public boolean isSolid();
	 public boolean isAlive();
	 public boolean isInPosition(Position pos);
	 public boolean interactWith(GameItem item);
	 public boolean receiveInteraction(Land land);
	 public boolean receiveInteraction(Mario obj);
	 public boolean receiveInteraction(ExitDoor exitDoor);
	 public boolean receiveInteraction(Goomba obj);
	 public boolean receiveInteraction(Mushroom mu);
	 public boolean receiveInteraction(Box box);
	 public boolean receiveInteraction (Grenade grenade); 
	 
	 public GameObject parse(String[] objWords, GameWorld game) throws GameParseException, OffBoardException;
	 
	 public boolean pushOut(Mario mario);
	 

}
