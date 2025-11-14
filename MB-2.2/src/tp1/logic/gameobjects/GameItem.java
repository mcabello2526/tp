package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position; 

public interface GameItem {
	 public boolean isSolid();
	 public boolean isAlive();
	 public boolean isInPosition(Position pos);
	 // They usually have to be in the same position to interact.
	 public boolean interactWith(GameItem item);
	 public boolean receiveInteraction(Land land);
	 public boolean receiveInteraction(Mario obj);
	 public boolean receiveInteraction(ExitDoor exitDoor);
	 public boolean receiveInteraction(Goomba obj);
	 public boolean receiveInteraction(Mushroom mu);
	 public boolean receiveInteraction(Box box);
	 //lo mismo no esta bien 
	 public GameObject parse(String[] objWords, GameWorld game);
}
