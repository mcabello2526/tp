package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.view.Messages;
import tp1.logic.GameModel; 
import java.util.Arrays;
import java.util.List;
import tp1.logic.Game; 
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.view.Messages;
public class GameObjectFactory {
	
	private static final List<GameObject> availableObjects = Arrays.asList(
			new Land(),
			new ExitDoor(),
			new Goomba(),
			new Mario(),
			new Mushroom(), 
			new Box()
	);


	public static GameObject parse (String objWords[], GameWorld game) throws ObjectParseException, OffBoardException {
        if (objWords == null || game == null) return null;
        
        GameObject obj = null; 
        for (GameObject o : availableObjects) {
        	obj = o.parse(objWords, game);
        }
        
        if (obj == null) {
        	throw new ObjectParseException(Messages.INVALID_GAME_OBJECT.formatted(objWords[0])); 
        }
        if (!obj.isInBoard()) {
            throw new OffBoardException(Messages.INVALID_POSITION.formatted(objWords[0]));
        }
        
      return obj; 
	}
	
}
