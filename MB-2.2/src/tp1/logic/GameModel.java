package tp1.logic;

import tp1.logic.gameobjects.GameObject;

public interface GameModel {
	
	public boolean isFinished();
	public void update();
	public void reset();
	public void reset(int level);
	public void addAction(Action act); 
	public void exit(); 
	public void finish();
	public boolean hasExited(); 
	
	public void addObject (GameObject obj); 

	public boolean addObjectFromString(String[] objWords); 

	
}
