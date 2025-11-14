package tp1.control.commands;

import tp1.logic.Game;
import tp1.view.GameView; 
import tp1.view.Messages; 
import tp1.logic.GameModel; 
public class UpdateCommand extends NoParamsCommand {
	
	private static final String NAME = Messages.COMMAND_UPDATE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
	private static final String HELP = Messages.COMMAND_UPDATE_HELP;

	public UpdateCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP); 
	}
	
// **** MANERA 1 ***************************************************************************
	/*
	@Override
	public Command parse(String[] commandWords) {
		
		if ( matchCommandName(commandWords[0]) ) {
			
			if (commandWords.length > 1) {
				this.helpText();
			}
			else {
				return this;
			}
	    }
		return null;
	}
	*/
		
// *******************************************************************************************
	
	
// **** MANERA 2 *****************************************************************************

    @Override
    public Command parse (String[] commandWords) {
        if (commandWords == null) {
            return null;
        }
        
        // 1. Check for the special "empty input" case.
        // This can be length 0 OR length 1 with an empty string.
        if (commandWords.length == 0 || (commandWords.length == 1 && commandWords[0].isEmpty())) {
            return this;
        }

        // 2. If not empty, it might be "u" or "update".
        // We delegate to the parent's (NoParamsCommand) strict parsing logic.
        return super.parse(commandWords); 
    }

// *******************************************************************************************
	
	
	@Override
	public void execute (GameModel game, GameView view) {
		// hace el update del juego 
		game.update(); 
		view.showGame();
	}

}
