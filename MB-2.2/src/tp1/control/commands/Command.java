package tp1.control.commands;

import tp1.logic.Game;
import tp1.view.GameView;
import tp1.logic.GameModel; 
import tp1.logic.GameWorld; 
public interface Command {

	public abstract void execute(GameModel game, GameView view);
	
	public Command parse(String[] commandWords);

	public String helpText();
}
