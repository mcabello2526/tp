package tp1.control.commands;

import tp1.logic.Game;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.GameModel; 
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
public class ExitCommand extends NoParamsCommand{

	
	private static final String NAME = Messages.COMMAND_EXIT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_EXIT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_EXIT_DETAILS;
	private static final String HELP = Messages.COMMAND_EXIT_HELP;

	public ExitCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP); 
	}
	
  	@Override
	public Command parse (String[] commandWords) throws CommandParseException {
	  return super.parse(commandWords); 
	}


	@Override
	public void execute(GameModel game, GameView view)throws CommandExecuteException{
	    game.exit(); 	 
	}
	

}
