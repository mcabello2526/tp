package tp1.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.GameModelException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.util.MyStringUtils;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration{
	
	private int remainingTime; 
	private int points; 
	private int lives; 
	private Mario mario; 
	private List<GameObject> npcs; 
	
	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException{
		npcs = new ArrayList<GameObject>();
		try (BufferedReader reader = new BufferedReader (new FileReader(fileName))){
			String datos = reader.readLine(); 
			if (datos == null) throw new GameLoadException(Messages.EMPTY_FILE); 
			
			String[] arrayDatos = MyStringUtils.splitWords(datos); 
			if (arrayDatos.length != 3) throw new GameLoadException("Invalid game status"); 
			
			this.remainingTime = Integer.parseInt(arrayDatos[0]); 
			this.points = Integer.parseInt(arrayDatos[1]);
			this.lives = Integer.parseInt(arrayDatos[2]); 
			
			while ((datos = reader.readLine()) != null) {
				if (datos.isEmpty()) continue; 
				String[] objWords = MyStringUtils.splitWords(datos); 
				
				Mario newMario = mario.parse(objWords, game);
				    
				GameObject obj = null; 
				    
				if (newMario != null) {
					this.mario = newMario ;
				}
				else {
					obj = GameObjectFactory.parse(objWords, game);
					npcs.add(obj); 
				}
			}	
		}catch (IOException | NumberFormatException | GameModelException e) {
			throw new GameLoadException("Error loading file: " + fileName, e); 
		}
	}
	
	@Override 
	public int getRemainingTime() {
		return this.remainingTime; 
	}
	
	@Override 
	public int points() { 
		return points; 
	}
	
	@Override
    public int numLives() { return lives; }
	
	@Override
    public Mario getMario() { return mario; }
	
	@Override
    public List<GameObject> getNPCObjects() { 
		return new ArrayList<>(this.npcs);
	}
}
