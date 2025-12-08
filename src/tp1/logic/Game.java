package tp1.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import tp1.logic.gameobjects.*;
import tp1.view.Messages;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.ObjectParseException;
import tp1.view.Messages;
import tp1.exceptions.GameModelException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.GameLoadException; 
import tp1.logic.GameConfiguration; 
public class Game implements GameModel, GameStatus, GameWorld{

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	private int remainingTime; 
	private int lives; 
	private int points;   
	private int nLevel;    
	
	private boolean finished = false; 
	private boolean win = false; 
	private boolean exit = false; 
	
	private Mario mario; 
	private GameObjectContainer gameObjects;
	
	private GameConfiguration fileLoader; 

	
	public Game(int nLevel) {
		this.nLevel = nLevel;
		this.mario = null; 
		gameObjects = new GameObjectContainer(); 
		this.lives = 3;  
		this.points = 0; 
		this.remainingTime = 100; 
		
		this.initLevel(this.nLevel); 
		
		
	}

// ******* LEVEL 0 **********************************************************************************
	private void initLevel0() {
		this.nLevel = 0;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}
		
		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		
		this.mario = new Mario(this, new Position(12,0));  //Game.DIM_Y-3, 0
		gameObjects.add(this.mario);

		
		gameObjects.add(new Goomba(this, new Position(0, 19)));  
		//gameObjects.add(new Goomba(this, new Position(12, 2))); //*
	}
	
// ****************************************************************************************************************************	
	
	
	
// ****** LEVEL 1 *************************************************************************************************************
	
	private void initLevel1() {
		this.nLevel = 1;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		
		
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}
		
		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));    
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(0, 19)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));
		gameObjects.add(new Goomba(this, new Position(4, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 6)));
		
	}
// ************************************************************************************************************************
	
	
	
	
// ******* CREATIVE MODE **************************************************************************************************
	
	private void initLevelVacio() {
		this.nLevel = -1; 
		this.remainingTime = 100 ;
		gameObjects = new GameObjectContainer(); 
		
	}
	
//*************************************************************************************************************************
	
//GAMEMODEL

	@Override
	public void update() {
		this.remainingTime--; 
		
		if (this.remainingTime < 0) this.remainingTime = 0; 
		
		
		if (this.remainingTime == 0 || this.numLives() == 0 ) {			
			this.finished = true; 
			this.win = false; 
		}		

		gameObjects.update(); 
		
	}
	
	@Override
	public void reset() {   
		if (this.fileLoader != null) {
			this.remainingTime = fileLoader.getRemainingTime(); 
			this.points = fileLoader.points(); 
			this.lives = fileLoader.numLives(); 
			
			this.gameObjects = new GameObjectContainer(); 
			
			this.mario = fileLoader.getMario();
			if (this.mario != null) {
				this.gameObjects.add(mario);
			}
			for (GameObject obj: fileLoader.getNPCObjects()) {
				this.gameObjects.add(obj);
			}
			
			this.finished = false;
            this.win = false;
            this.exit = false;
		}
		else {
			int puntosSalvados = this.points; 			
			this.initLevel(this.nLevel); 			
		    this.points = puntosSalvados;
		}
		

	  
	}
	
	@Override
	public void reset(int level) {
		this.fileLoader = null; 
		
		if (level == -1) {
			initLevelVacio(); 
			points = 0; 
			lives = 3; 
		}
		else {
			int puntosSalvados = this.points; 
			this.initLevel(level);
		    this.points = puntosSalvados;
		}
		
		nLevel = level; 
	    reset();
	}
	
	@Override
	public void addAction (Action act) {  
		mario.addAction(act);
	}
	
	@Override
	public void exit() {  
		this.exit = true;
		this.finished = false; 
	}
	
	@Override
	public void finish() {
		this.finished = true; 
	}
	
	@Override
	public void addObject (GameObject o) {
		gameObjects.addToPending(o);
	}
	
	@Override
	public boolean hasExited() {  
		return exit && !finished;  
	}
	
	@Override
	public boolean isFinished() {
		return this.finished; 
	}
	
	@Override
	public boolean parse (String[] objWords) throws OffBoardException, GameParseException {
	    if (objWords == null || objWords.length == 0) return false;
	    
	    Mario newMario = mario.parse(objWords, this);
	    
	    GameObject obj = null; 
	    
	    if (newMario != null) {
	    	mario.dead();
	    	gameObjects.add(newMario); 
	    	mario = newMario; 
	    }
	    else {
			obj = GameObjectFactory.parse(objWords, this);
			if (obj == null) {
				throw new ObjectParseException(Messages.INVALID_GAME_OBJECT.formatted(String.join(" ", objWords)));
			}
			if (!obj.isInBoard()) {
				throw new OffBoardException(Messages.OFF_BOARD_POSITION.formatted(String.join(" ", objWords)));
			}
			gameObjects.add(obj);
	    }
	    
	    return true; 
	}
	
	@Override 
	public void load (String fileName) throws GameLoadException{
		//GameConfiguration config = new FileGameConfiguration(fileName, this);
		this.fileLoader = new FileGameConfiguration(fileName, this);
		
		this.remainingTime = fileLoader.getRemainingTime(); 
		this.points = fileLoader.points(); 
		this.lives = fileLoader.numLives(); 
		
		this.gameObjects = new GameObjectContainer(); 
		
		this.mario = fileLoader.getMario();
		this.gameObjects.add(this.mario);
		
		for (GameObject obj : fileLoader.getNPCObjects()) {
	        this.gameObjects.add(obj);
	    }
		
		reset(); 
	}

// ****************************************************************************************************************
	
// GAMESTATUS 
	
	@Override
	public String positionToString (int col, int row) { 
		if (row < 0 || row >= DIM_Y || col < 0 || col >= DIM_X || gameObjects == null) return Messages.EMPTY;
		    return this.gameObjects.positionToString(new Position(row,col));  
	}
	

	@Override
	public int points() {
		return this.points; 
	}

	@Override
	public int numLives() {
		return this.lives; 
	}
	
	@Override
	public int numLevel() {
		return this.nLevel; 
	}

	@Override
	public int remainingTime() {   
		return this.remainingTime; 
	}
	
	@Override 
	public boolean playerWins() {
		return this.win; 
	}

	@Override
	public boolean playerLoses() {
		return !this.win && this.finished; 
	}
	
	@Override 
	public void resetTime() { 
		this.remainingTime = 100;   
	}
	
// **********************************************************************************************
	
//GAMEWORLD
	@Override
	public boolean isSolid(Position p) {
		return this.gameObjects.isSolid(p); 
	}

	@Override
	public boolean moreThanZeroLives() {
		return lives > 0; 
	}
	
	@Override
	public void addPoints(int x) {
		this.points += x;
	}

	@Override
	public void reduceNumLives() {  
		if (this.lives > 0) {
			this.lives--; 
		}
		else {
			this.finished = true; 
			this.win = false; 
		}
	}
	
	@Override
	public void marioExited() {	 
		this.points += this.remainingTime*10 ; 
		this.remainingTime = 0;
		this.win = true; 
		this.finished = true; 
		
	}
	
	@Override
	public void doInteractionFrom(GameItem item) {
		gameObjects.doInteraction(item);
		
	}
	
//************************************************************************************

// OTRAS
	/*
	@Override
	public String toString() {	// representacion textual del game 
		
		StringBuilder sb = new StringBuilder(); 
	    sb.append(System.lineSeparator());

	    
	    for (int r = 0; r < DIM_Y; r++) {
	        for (int c = 0; c < DIM_X; c++) {
	            sb.append(positionToString(r, c));
	        }
	        sb.append(System.lineSeparator());
	    }
	    return sb.toString();
	}
	*/
	
	@Override
	public String toString() {
	    
	    StringBuilder sb = new StringBuilder();
	    sb.append(this.remainingTime).append(" ")
	      .append(this.points).append(" ")
	      .append(this.lives).append(System.lineSeparator());
	    
	    
	    sb.append(gameObjects.toString()); 
	    return sb.toString();
	}
	

	private final void initLevel(int level) {
		switch(level) {
		case 0: 
			initLevel0(); 
			break; 
		case -1: 
			initLevelVacio(); 
			break; 
		default: 
			this.initLevel1(); 
			break; 
		}
	}
	
	public void save(String fileName) throws GameModelException{
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        writer.write(this.toString());
	    } catch (IOException e) {
	        throw new GameModelException(Messages.WRITE_FILE_ERROR.formatted(fileName), e);
	    }
	}
	
}

















