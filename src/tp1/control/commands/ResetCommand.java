package tp1.control.commands;

import tp1.view.Messages;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.view.GameView;
import tp1.logic.GameModel; 

public class ResetCommand extends AbstractCommand {
	
		private final int numLevel; 
		private static final int NO_LEVEL_PARAM = -3; 
		
	    private static final String NAME = Messages.COMMAND_RESET_NAME;
	    private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	    private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	    private static final String HELP = Messages.COMMAND_RESET_HELP;


	    public ResetCommand (int numLevel) {
	    	super(NAME, SHORTCUT, DETAILS, HELP); 
	    	this.numLevel = numLevel; 
	    }
	    

	    
	    public ResetCommand() {
	    	super (NAME, SHORTCUT, DETAILS, HELP); 
	    	this.numLevel = NO_LEVEL_PARAM;  
	    }
			    
	    @Override
	    public Command parse(String[] commandWords) throws CommandParseException {
	        if (!matchCommandName(commandWords[0])) {
	            return null;
	        }
       
	        if (commandWords.length == 1) {
	        	return new ResetCommand() ;
	        }
	       
	        if (commandWords.length > 2) {
	            throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	        }

	        try {	          
	            int level = Integer.parseInt(commandWords[1]);
	            
	            if (level == 0 || level == 1 || level == -1) {
	               
	            }	            	            
	            return new ResetCommand(level);
	            
	        } catch (NumberFormatException nfe) {
	            throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), nfe);
	        }
	    }
	    
	    
	    @Override
		public void execute (GameModel game, GameView view) throws CommandExecuteException {
	    	if (this.numLevel == NO_LEVEL_PARAM) {
	            game.reset(); 
	            view.showGame();
	        }
	    	else if (this.numLevel == 1 || this.numLevel == 0 || this.numLevel == -1) {
				game.reset(numLevel);
				view.showGame();
			}
			else {			
				throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER); 	
			}	
		}

	
}


