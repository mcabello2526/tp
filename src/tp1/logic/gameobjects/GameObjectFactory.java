package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.view.Messages;
import tp1.logic.GameModel; 
import java.util.Arrays;
import java.util.List;
import tp1.logic.Game; 

public class GameObjectFactory {
	
	private static final List<GameObject> availableObjects = Arrays.asList(
			new Land(),
			new ExitDoor(),
			new Goomba(),
			new Mario(),
			new Mushroom(), 
			new Box()
	);


	public static GameObject parse (String objWords[], GameWorld game) /*throws ObjectParseException*/ {
        if (objWords == null || game == null) return null;
        
        for (GameObject o : availableObjects) {
            GameObject obj = o.parse(objWords, game);
            if (obj != null) return obj;
        }
        
        return null;
        //throw new ObjectParseException (Messages.INVALID_GAME_OBJECT.formatted(String.join(" ", objWords))); 
	}
	
}
