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
	private List<String> npcsEnString; 
	private GameWorld game; 
	
	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException{
		this.game = game; 
		npcsEnString = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader (new FileReader(fileName))){
			String datos = reader.readLine(); 
			if (datos == null) throw new GameLoadException(Messages.EMPTY_FILE); 
			
			String[] arrayDatos = MyStringUtils.splitWords(datos); 
			if (arrayDatos.length != 3) throw new GameLoadException(Messages.INVALID_GAME_STATUS.formatted(datos)); 
			
			this.remainingTime = Integer.parseInt(arrayDatos[0]); 
			this.points = Integer.parseInt(arrayDatos[1]);
			this.lives = Integer.parseInt(arrayDatos[2]); 
			
			while ((datos = reader.readLine()) != null) {
				if (!datos.isEmpty()) {
					npcsEnString.add(datos); 
				}
				/*
				if (datos.isEmpty()) continue; 
				String[] objWords = MyStringUtils.splitWords(datos); 
				
				Mario newMario = new Mario().parse(objWords, game);
				    
				GameObject obj = null; 
				    
				if (newMario != null) {
					this.mario = newMario ;
				}
				else {
					obj = GameObjectFactory.parse(objWords, game);
					npcs.add(obj); 
				}
				*/
			}	
		} catch (IOException | NumberFormatException e) {
	        throw new GameLoadException(Messages.READ_FILE_ERROR.formatted(fileName), e);
	        
	    } 
		/*catch (GameParseException | NumberFormatException e) {
	        throw new GameLoadException(Messages.GAME_LOAD_ERROR.formatted(fileName), 
	              new GameParseException(Messages.INVALID_FILE_CONFIGURATION.formatted(fileName), e));
	    }
		*/
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
    public Mario getMario() {
		for (String datos : npcsEnString) {
			String[] arrayDatos = MyStringUtils.splitWords(datos); 
			if (arrayDatos.length > 1 && (arrayDatos[1].equalsIgnoreCase("MARIO")|| arrayDatos[1].equalsIgnoreCase("M"))) {
				try {
					return new Mario().parse(arrayDatos, game);
				} catch (GameParseException e) {
					e.printStackTrace();
				} 
			}
		}
		return null; 
	}
	
	@Override
    public List<GameObject> getNPCObjects(){ 
		List<GameObject>npcs = new ArrayList<>(); 
		for (String datos: npcsEnString) {
			String[] arrayDatos = MyStringUtils.splitWords(datos); 
			if (arrayDatos.length > 1 && !arrayDatos[1].equalsIgnoreCase("MARIO") && !arrayDatos[1].equalsIgnoreCase("M")) {
				GameObject obj = null; 
				try {
					obj = GameObjectFactory.parse(arrayDatos, game); 
					if (obj != null) npcs.add(obj); 
					
				}catch (GameParseException e) {
					//throw new GameParseException (Messages.INVALID_GAME_OBJECT.formatted(obj)); 
				}
			}
		}
		return npcs; 
	}
}
