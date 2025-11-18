package tp1.control;

import tp1.control.commands.Command;
import tp1.control.commands.CommandGenerator;
import tp1.logic.Game;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.GameModel; 

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}


	/**
	 * Runs the game logic, coordinate Model(game) and View(view)
	 */
	public void run() {

		view.showWelcome();

		view.showGame();
		
		while (!game.isFinished() && !game.hasExited()) {
			String[] words = view.getPrompt();
			Command command = CommandGenerator.parse(words);

			if (command != null)
				command.execute(game, view);
				//view.showGame(); 
			/*else 
				view.showError(Messages.UNKNOWN_COMMAND.formatted(String.join(" ", words)));
			*/
		}
		view.showEndMessage();
	}
}
