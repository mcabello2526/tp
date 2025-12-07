package tp1.control.commands;

import tp1.view.Messages;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import java.util.*; 
import tp1.logic.Game;
import tp1.view.GameView;
import tp1.logic.GameModel; 

public class ResetCommand extends AbstractCommand {
	
		private final int numLevel; 
		private int prevNumLevel = 1; //N
		private final static int invalid = 2; 
		
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
	    	this.numLevel = prevNumLevel;  // antes estaba a -2
	    }
		
/*
	 @Override   
	    public Command parse(String[] commandWords) throws CommandParseException {
	    	if (commandWords [0] == null || commandWords.length == 0) return null;
	    	 
	    	
	    	if (matchCommandName(commandWords[0])) {
	    		
		    	if (commandWords.length == 1) {
		    	
    				if (this.prevNumLevel == 1 || this.prevNumLevel == 0 || this.prevNumLevel == -1) {
    					return new ResetCommand(this.prevNumLevel); 
    				}
    				else return new ResetCommand(); 
    				
		    	}
		    	
		    	else if (commandWords.length > 1){
		    		
		    		String nivelString = commandWords[1]; 
		    		
		    	    if ("-1".equals(nivelString)) {
		    	    	prevNumLevel = -1; //N
		    	    	return new ResetCommand(-1);
		    	    }
		    	    else if ("0".equals(nivelString)) {
		    	    	prevNumLevel = 0; //N
		    	    	return new ResetCommand(0);
		    	    }
		    	    else if ("1".equals(nivelString)) {
		    	    	prevNumLevel = 1; //N
		    	    	return new ResetCommand(1);
		    	    }
		    	    else return new ResetCommand(invalid); 
	
		    	}
	    	}
	    	return null; 
	    }
	 
*/	    
	    @Override
	    public Command parse(String[] commandWords) throws CommandParseException {
	        if (!matchCommandName(commandWords[0])) {
	            return null;
	        }
       
	        if (commandWords.length == 1) {
	            return new ResetCommand(this.prevNumLevel);
	        }
	       
	        if (commandWords.length > 2) {
	            throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	        }

	        try {	          
	            int level = Integer.parseInt(commandWords[1]);
	            
	            if (level == 0 || level == 1 || level == -1) {
	                this.prevNumLevel = level;
	            }	            	            
	            return new ResetCommand(level);
	            
	        } catch (NumberFormatException nfe) {
	            throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), nfe);
	        }
	    }
	    
	    
	    @Override
		public void execute (GameModel game, GameView view) throws CommandExecuteException{

			if (this.numLevel == 1 || this.numLevel == 0 || this.numLevel == -1) {
				game.reset(numLevel);
				view.showGame();
			}
			else {
				view.showError(Messages.INVALID_LEVEL_NUMBER); 	
			}	
		}

	
}


