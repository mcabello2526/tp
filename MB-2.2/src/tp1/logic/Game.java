package tp1.logic;

import tp1.logic.gameobjects.*;
import tp1.view.Messages;
import tp1.logic.GameObjectContainer;
public class Game implements GameModel, GameStatus, GameWorld{

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	private int remainingTime; 
	private int lives; 
	private int points;   // no se resetea 
	private int nLevel;   // no se resetea 
	
	private boolean finished = false; 
	private boolean win = false; 
	
	private Mario mario; 
	private GameObjectContainer gameObjects; 

	
	public Game(int nLevel) {
		this.nLevel = nLevel;
		this.mario = null; 
		gameObjects = new GameObjectContainer(); 
		this.lives = 3;  
		this.points = 0; 
		this.remainingTime = 100; 
		
		this.initLevel(this.nLevel); 
		
		
	}
	
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
	}
	
	private void initLevel1() {
		this.nLevel = 1;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		
		gameObjects.add(new Mushroom(this, new Position(12,8)));
		gameObjects.add(new Mushroom(this, new Position(2,20)));
		gameObjects.add(new Box (this, new Position (9, 4)));
		
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
	
	private void initLevelVacio() {
		this.nLevel = -1; 
		this.remainingTime = 100 ;
		gameObjects = new GameObjectContainer(); 
		
	}
	
	public String positionToString (int col, int row) {  // se usa en ConsoleView y se usa en toString Game
		if (row < 0 || row >= DIM_Y || col < 0 || col >= DIM_X || gameObjects == null) return Messages.EMPTY;
		    return this.gameObjects.getIcon(new Position(row,col));  
	}


	public boolean isSolid(Position p) {
		return this.gameObjects.isSolid(p); 
	}

	public boolean playerWins() {
		return this.win; 
	}

	public boolean playerLoses() {
		return !this.win && this.finished; 
	}
	
	public void exit() {
		this.finished = true; 
	}
	public boolean isFinished() {
		return this.finished; 
	}
	
	public void resetTime() { // este solo se deberia llamar cuando se cambie de mapa, no cuando mario muera
		this.remainingTime = 100;  // por poner, no lo llamamos nunca 
	}
	
	public boolean moreThanZeroLives() {
		return lives > 0; 
	}
	
	public int remainingTime() {   
		return this.remainingTime; 
	}
	
	public int points() {
		return this.points; 
	}

	public void addPoints(int x) {
		this.points += x;
	}

	
	public int numLives() {
		return this.lives; 
	}
	
	public int numLevel() {
		return this.nLevel; 
	}


	public void reduceNumLives() {  // reduce el numero de vidas y ademas comprueba si han llegado a 0
		if (this.lives > 0) {
			this.lives--; 
		}
		else {
			this.finished = true; 
			this.win = false; 
		}
	}


	@Override
	public String toString() {	// representacion textual del game // no se invoca en ningun sitio				
		
		
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
	
	
// si en el juego se hace un reset hay que mirar si el juego si el tiempo se queda 
	public void reset() {   // reset sin parametros --> mas para el contoller que el juego
		int puntosSalvados = this.points; 
		//int tiempoRestante = this.remainingTime; 
		
		this.initLevel(this.nLevel); 
		// nuevo
	    this.points = puntosSalvados;
	    //this.remainingTime = tiempoRestante;
	}
	
	public void reset(int level) {
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

	    
	}
	
	
	private void initLevel(int level) {
		switch(level) {
		case 0: 
			initLevel0(); 
			break; 
		
		default: 
			this.initLevel1(); 
			break; 
		}
	}
	
	
	public void update() {
		
		this.remainingTime--; 
		
		if (this.remainingTime < 0) this.remainingTime = 0; 
		
		
		if (this.remainingTime == 0 || this.numLives() == 0 ) {			
			this.finished = true; 
			this.win = false; 
		}		

		gameObjects.update(); 
		
	}

//**** ESTO HAY QUE CAMBIARLO ***********************************************
	
	public void addAction (Action act) {  // añade a la actionList de Mario
		mario.addAction(act);
	}
	
// **************************************************************************
	public void marioExited() {	 
		this.points += this.remainingTime*10 ; 
		this.remainingTime = 0;
		this.win = true; 
		this.finished = true; 
		
	}
	
	public void doInteractionsFrom(GameItem item) {	// invoca al hacer interacciones del container 
		gameObjects.doInteraction(item);
		
	}
	
	public void addObject (GameObject o) {
		gameObjects.add(o);
	}

	@Override
	public boolean addObjectFromString(String[] objWords) {
	    if (objWords == null || objWords.length == 0) return false;
	    
	    GameObject obj = GameObjectFactory.parse(objWords, this);
	    if (obj == null) return false; 
	    
	    addObject(obj);
	    
	    return true; 
	}

/*
	public boolean hitFromBelow (Position pos, Mario m) {
		return gameObjects.belowHit(pos, m); 
	}
*/
}

















