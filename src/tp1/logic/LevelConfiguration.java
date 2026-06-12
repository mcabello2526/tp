package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

public class LevelConfiguration implements GameConfiguration {

    private int remainingTime;
    private int points;
    private int lives;
    private int level;
    private Mario mario;
    private List<GameObject> npcs;

   
    public LevelConfiguration(GameWorld game) throws GameLoadException {
        this(1, game);
    }

// NIVELES 
    public LevelConfiguration(int level, GameWorld game) throws GameLoadException {
        this.level = level;
        this.points = 0;
        this.lives = 3;
        this.npcs = new ArrayList<>();

        switch (level) {
            case 0:
                initLevel0(game);
                break;
            case 1:
                initLevel1(game);
                break;
            case -1:
                initLevelVacio(game);
                break;
            default:
                throw new GameLoadException(Messages.INVALID_LEVEL_NUMBER);
        }
    }

// NIVEL 0
    private void initLevel0(GameWorld game) {
        this.remainingTime = 100;
        for(int col = 0; col < 15; col++) {
            npcs.add(new Land(game, new Position(13,col)));
            npcs.add(new Land(game, new Position(14,col)));		
        }
        npcs.add(new Land(game, new Position(Game.DIM_Y-3,9)));
        npcs.add(new Land(game, new Position(Game.DIM_Y-3,12)));
        for(int col = 17; col < Game.DIM_X; col++) {
            npcs.add(new Land(game, new Position(Game.DIM_Y-2, col)));
            npcs.add(new Land(game, new Position(Game.DIM_Y-1, col)));		
        }
        npcs.add(new Land(game, new Position(9,2)));
        npcs.add(new Land(game, new Position(9,5)));
        npcs.add(new Land(game, new Position(9,6)));
        npcs.add(new Land(game, new Position(9,7)));
        npcs.add(new Land(game, new Position(5,6)));
        
        @SuppressWarnings("unused")
		int tamX = 8, tamY= 8;
        int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
        for(int col = 0; col < tamX; col++) {
            for (int fila = 0; fila < col+1; fila++) {
                npcs.add(new Land(game, new Position(posIniY- fila, posIniX+ col)));
            }
        }
        npcs.add(new ExitDoor(game, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

        this.mario = new Mario(game, new Position(12,0));
        npcs.add(new Goomba(game, new Position(0, 19)));  
    }
    
// NIVEL 1 

    private void initLevel1(GameWorld game) {
        this.remainingTime = 100;
        for(int col = 0; col < 15; col++) {
            npcs.add(new Land(game, new Position(13,col)));
            npcs.add(new Land(game, new Position(14,col)));		
        }
        npcs.add(new Land(game, new Position(Game.DIM_Y-3,9)));
        npcs.add(new Land(game, new Position(Game.DIM_Y-3,12)));
        for(int col = 17; col < Game.DIM_X; col++) {
            npcs.add(new Land(game, new Position(Game.DIM_Y-2, col)));
            npcs.add(new Land(game, new Position(Game.DIM_Y-1, col)));		
        }
        npcs.add(new Land(game, new Position(9,2)));
        npcs.add(new Land(game, new Position(9,5)));
        npcs.add(new Land(game, new Position(9,6)));
        npcs.add(new Land(game, new Position(9,7)));
        npcs.add(new Land(game, new Position(5,6)));
        
        @SuppressWarnings("unused")
		int tamX = 8, tamY= 8;
        int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
        for(int col = 0; col < tamX; col++) {
            for (int fila = 0; fila < col+1; fila++) {
                npcs.add(new Land(game, new Position(posIniY- fila, posIniX+ col)));
            }
        }
        npcs.add(new ExitDoor(game, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

        this.mario = new Mario(game, new Position(Game.DIM_Y-3, 0));    

        npcs.add(new Goomba(game, new Position(0, 19)));
        npcs.add(new Goomba(game, new Position(12, 14)));
        npcs.add(new Goomba(game, new Position(12, 11)));
        npcs.add(new Goomba(game, new Position(10, 10)));
        npcs.add(new Goomba(game, new Position(12, 8)));
        npcs.add(new Goomba(game, new Position(4, 6)));
        npcs.add(new Goomba(game, new Position(12, 6)));
        
       // npcs.add(new Mushroom(game, new Position(12, 8)));
       // npcs.add(new Box(game, new Position(9, 4)));
    }

// NIVEL -1
    private void initLevelVacio(GameWorld game) {
        this.remainingTime = 100;
        this.mario = null;
    }

    public int getLevel() { return level; }
    @Override 
    public int getRemainingTime() { return remainingTime; }
    @Override 
    public int points() { return points; }
    @Override 
    public int numLives() { return lives; }
    @Override 
    public Mario getMario() { return mario; }
    @Override 
    public List<GameObject> getNPCObjects() { return npcs; }
    

}
