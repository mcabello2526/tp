package tp1.control;

import tp1.control.commands.Command;
import tp1.control.commands.CommandGenerator;
import tp1.exceptions.CommandException; 
import tp1.view.GameView;
import tp1.logic.GameModel; 

public class Controller {

	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}

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
