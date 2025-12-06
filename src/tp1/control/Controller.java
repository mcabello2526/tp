package tp1.control;

import tp1.control.commands.Command;
import tp1.control.commands.CommandGenerator;
import tp1.exceptions.CommandException; 
import tp1.logic.Game;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.GameModel; 
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;

public class Controller {

	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}

/*	
	public void run() {

		view.showWelcome();

		view.showGame();
		
		while (!game.isFinished() && !game.hasExited()) {
			
			String[] words = view.getPrompt();
			Command command = CommandGenerator.parse(words); 

			if (command != null)
				command.execute(game, view); 
			else 
				view.showError(Messages.UNKNOWN_COMMAND.formatted(String.join(" ", words)));
			
		}
		
		view.showEndMessage();

	}
*/
	public void run() {
        view.showWelcome();
        view.showGame();

        while (!game.isFinished() && !game.hasExited()) {
            
            try {
                String[] words = view.getPrompt();
                Command command = CommandGenerator.parse(words); 
                
                if (command != null) {
                    command.execute(game, view); 
                }
                
                
            } catch (CommandException e) {
                
                view.showError(e.getMessage());
                Throwable cause = e.getCause();
                while (cause != null) {
                    view.showError(cause.getMessage());
                    cause = cause.getCause();
                }
            }
        }
        view.showEndMessage();
    }
}
