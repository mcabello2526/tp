package tp1.logic;


import tp1.logic.gameobjects.GameObject;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.GameLoadException;
public interface GameModel {
	
	
	public void update();
	public void reset();
	public void reset(int level);
	public void addAction(Action act); 
	public void exit(); 
	public void finish();
	public void addObject (GameObject obj);
	public void save(String filename) throws GameModelException; 
	public void load(String fileName) throws GameLoadException; 
	
	public boolean hasExited(); 
	public boolean isFinished();
	public boolean parse(String[] objWords) throws OffBoardException, ObjectParseException, GameParseException; 

	
}
