package tp1.logic;
import java.util.List;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;
import tp1.exceptions.*;
public interface GameConfiguration {
	public int getRemainingTime();
    public int points();
    public int numLives();
    public Mario getMario();
    public List<GameObject> getNPCObjects();
}
