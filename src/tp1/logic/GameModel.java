package tp1.logic;

import tp1.logic.gameobjects.GameObject;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.ObjectParseException;
public interface GameModel {
	
	
	public void update();
	public void reset();
	public void reset(int level);
	public void addAction(Action act); 
	public void exit(); 
	public void finish();
	public void addObject (GameObject obj) ; 
	
	public boolean hasExited(); 
	public boolean isFinished();
	public boolean parse(String[] objWords) throws OffBoardException, ObjectParseException  ; 

	
}
