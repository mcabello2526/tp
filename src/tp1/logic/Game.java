package tp1.logic;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.GameLoadException;

public class Game implements GameModel, GameStatus, GameWorld {

    public static final int DIM_X = 30;
    public static final int DIM_Y = 15;

    private static final int INITIAL_LIVES = 3;
    private static final int MINIMUM_POINTS  = 0;
    private static final int INITIAL_REMAINING_TIME = 100;
    private static final int EXIT_POINTS = 10;
    private static final int MINIMUM_VALUE = 0; 

    private int remainingTime;
    private int lives;
    private int points;
    private int grenadeLives; 
    private int grenadeCount; 

    private boolean finished;
    private boolean win;
    private boolean exit;

    private Grenade grenade; 
    private Mario mario;
    private GameObjectContainer gameObjects;

    private GameConfiguration fileLoader;


    public Game(int nLevel) {
        this.points = MINIMUM_POINTS;
        this.lives = INITIAL_LIVES;

        try {
        	// el primer mapa siempre se carga de la clase de los niveles (mapa 1) 
            this.fileLoader = new LevelConfiguration(nLevel, this);
        } catch (GameLoadException e) {
            try { this.fileLoader = new LevelConfiguration(this); } catch (GameLoadException ex) {}
        }

        this.setGameState();
        this.setPlayerState();
    }


// **** AUXILIARES *******************************************************************************************
    private void setGameState() {
    	//tiempo 
        this.remainingTime = fileLoader.getRemainingTime(); 
        
        //contenedor de objetos 
        this.gameObjects = new GameObjectContainer(); 
        
        // mario 
        this.mario = fileLoader.getMario();
        if (this.mario != null) {
        	// copia del mario 
            this.mario = this.mario.copy(this); 
            
            // lo añadimos a los objetos la copia 
            this.gameObjects.add(this.mario);
        }
        
        // añadimos una copia de los objetos para evitar los cambios por referencia 
        for (GameObject obj : fileLoader.getNPCObjects()) {
            this.gameObjects.add(obj.copy(this));
        }
        
        
        this.finished = false;
        this.win = false;
        this.exit = false;
    }


    private void setPlayerState() {
        this.points = fileLoader.points();
        this.lives  = fileLoader.numLives();
    }


//**** LOGICA PRINCIPAL *********************************************************************************************
    @Override
    public void reset() {
        this.setGameState();
    }

    @Override
    public void reset(int level) {
        try {
            this.fileLoader = new LevelConfiguration(level, this);
        } catch (GameLoadException e) {
            try { this.fileLoader = new LevelConfiguration(this); } catch (GameLoadException ex) {}
        }

        this.setGameState();
        
        if (level == -1) {
            this.points = MINIMUM_POINTS;
            this.lives = INITIAL_LIVES;
        }
        
    }

    @Override
    public void update() {
    	// modificamos el tiempo
        this.remainingTime--;
        
        // lo dejamos en cero 
        if (this.remainingTime < MINIMUM_VALUE) this.remainingTime = MINIMUM_VALUE;
        
        //si llega el tiempo a 0 o se nos acaban las vidas, terminamos el juego 
        if (this.remainingTime == MINIMUM_VALUE || this.numLives() == MINIMUM_VALUE) {
            this.finished = true;
            this.win = false;
        }

        // actualizamos objetos 
        gameObjects.update();
    }


    @Override
    public void load(String fileName) throws GameLoadException {
    	// creamos la instancia de la clase FileGameConfiration que lee archivos 
        this.fileLoader = new FileGameConfiguration(fileName, this);

        // ponemos las variables de estado del juego y del jugador 
        this.setGameState();
        this.setPlayerState();
    }

    @Override
    public void save(String fileName) throws GameModelException {
    	// salvamos la partida escribiendo con el buffer en el fichero
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(this.toString());
            
        } catch (IOException e) {
            throw new GameModelException(Messages.WRITE_FILE_ERROR.formatted(fileName), e);
        }
    }

    @Override
    public void addObject(String[] objWords) throws OffBoardException, GameParseException {
        if (objWords == null || objWords.length == 0) return;
        
        // vemos si el objeto que queremos instanciar es mario
        Mario newMario = new Mario().parse(objWords, this);
        
        // si es mario ...
        if (newMario != null) {
        	
        	// si ya teniamos mario, eliminamos el actual
            if (this.mario != null) {
                this.mario.deathByReplacement();
            }
           
            //actualizamos su referencia y añadimos al contenedor 
            this.mario = newMario;
            gameObjects.add(newMario);
            
            //llamamos a las posibles interacciones que pueda haber en la posicion creada
            this.doInteractionFrom(newMario);  // 
            return;
        }

        // si no es mario, es un npc
        GameObject obj = GameObjectFactory.parse(objWords, this);
        if (obj != null) {  //
            gameObjects.add(obj);
            
            this.doInteractionFrom(obj); //
        }
    }

    @Override
    public void addAction(Action act) {
        gameObjects.addAction(act);
    }

    @Override
    public void doInteractionFrom(GameItem item) {
        gameObjects.doInteraction(item);
    }


//**** ENCAPSUALCION DE LA LOGICA *****************************************************************************

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
    public void addObject(GameObject o) {
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
    public int points() {
        return this.points;
    }

    @Override
    public int numLives() {
        return this.lives;
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
        this.remainingTime = INITIAL_REMAINING_TIME;
    }

    @Override
    public boolean isSolid(Position p) {
        return this.gameObjects.isSolid(p);
    }

    @Override
    public boolean moreThanZeroLives() {
        return lives > MINIMUM_VALUE;
    }

    @Override
    public void addPoints(int x) {
        this.points += x;
    }

    @Override
    public void reduceNumLives() {
    	//si es mayor que el numero minimo, quitamos una vida 
        if (this.lives > MINIMUM_VALUE) {
            this.lives--;
        }
        // comprobamos que no se haya quedado en 0: el juego termina 
        if (this.lives == MINIMUM_VALUE) {
            this.finished = true;
            this.win = false;
        }
    }

    @Override
    public void marioExited() {
    	// sumamos a los puntos el tiempo * 10
        this.points += this.remainingTime * EXIT_POINTS;
        
        // tiempo = 0
        this.remainingTime = MINIMUM_VALUE;
        
        this.win = true;
        this.finished = true;
    }
    
    public void shootGrenade() {
    	Position posG =
    	this.grenade = new Grenade(this, )
    }


//**** REPRESENTACION TEXTUAL DEL JUEGO *******************************************************************

    @Override
    public String positionToString(int col, int row) {
        if (row < 0 || row >= DIM_Y || col < 0 || col >= DIM_X || gameObjects == null)
            return Messages.EMPTY;
        return this.gameObjects.positionToString(new Position(row, col));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.remainingTime).append(" ")
          .append(this.points).append(" ")
          .append(this.lives).append(System.lineSeparator());
        
        sb.append(gameObjects.toString());
        
        return sb.toString();
    }
}

















