package tp1.logic;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameItem; 

public interface GameWorld {
	
    public boolean isSolid(Position p);
    public boolean moreThanZeroLives();
    
    public void addPoints(int x);
    public void reduceNumLives();
    public void exit();
    public void reset(); 
    public void marioExited(); 
    public void addObject(GameObject obj); 
    
    public void doInteractionFrom(GameItem item); 
}
