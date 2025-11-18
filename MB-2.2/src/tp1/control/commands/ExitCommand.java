package tp1.control.commands;

import tp1.logic.Game;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.GameModel; 

public class ExitCommand extends NoParamsCommand{

	// Forman parte de atributos de estado
	private static final String NAME = Messages.COMMAND_EXIT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_EXIT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_EXIT_DETAILS;
	private static final String HELP = Messages.COMMAND_EXIT_HELP;

	public ExitCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP); 
	}
	
// ***** MANERA 1 *********************************************************************************************
/*  --> no se si hay que añadir un parse en cada una de las subclases ??
	@Override   
	public Command parse(String[] commandWords) {
		if (commandWords == null) return null; 
		if (matchCommandName(commandWords[0]) ) {
			return this;
		}	
		return null;
	}
*/
// ************************************************************************************************************
	
// **** MANERA 2 **********************************************************************************************

  	@Override
	public Command parse (String[] commandWords) {
	  return super.parse(commandWords); 
	}

// ************************************************************************************************************

	@Override
	public void execute(GameModel game, GameView view){
		// You should let the game know that you are leaving so that 
		// if it needs to close something, it can close it and finish.
	    game.exit(); 	
	    //view.showEndMessage(); 
	}

}
