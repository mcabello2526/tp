package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.view.Messages;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;

public class GameObjectFactory {
	
	private static final List<GameObject> availableObjects = Arrays.asList(
			new Land(),
			new ExitDoor(),
			new Goomba(),
			new Mario(),
			new Mushroom(), 
			new Box(), 
			new Grenade()
	);


	public static GameObject parse(String objWords[], GameWorld game) throws GameParseException, OffBoardException {
        if (objWords == null || game == null) return null;
        
        GameObject obj = null; 
        for (GameObject o : availableObjects) {
			obj = o.parse(objWords, game);
        	if (obj != null) break; 
        }
        
        if (obj == null) {
        	throw new ObjectParseException(Messages.INVALID_GAME_OBJECT.formatted(String.join(" ", objWords))); 
        }
        
       
        if (!obj.isInBoard()) {
            throw new OffBoardException(Messages.OFF_BOARD_POSITION.formatted(String.join(" ", objWords)));
        }
 
        return obj; 
	}
}
