package tp1.logic;

import tp1.logic.gameobjects.GameObject;
public interface GameWorld {
	
    public boolean isSolid(Position p);
    public void addPoints(int x);
    public void reduceNumLives();
    public boolean moreThanZeroLives();
    public void exit();
    public void reset(); 
    public void marioExited(); 
    public void addObject(GameObject obj); 
    
   
}
