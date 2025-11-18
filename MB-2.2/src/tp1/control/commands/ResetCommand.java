package tp1.control.commands;

import tp1.view.Messages;
import tp1.logic.Action;
import java.util.*; 
import tp1.logic.Game;
import tp1.view.GameView;
import tp1.logic.GameModel; 
public class ResetCommand extends AbstractCommand {
	
		private final int numLevel; 
		
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
	    	this.numLevel = -2;  
	    }
		

	   
	    public Command parse(String[] commandWords) {
	    	if (commandWords [0] == null || commandWords.length == 0) return null;
	    	 
	    	
	    	if (matchCommandName(commandWords[0])) {
	    		
		    	if (commandWords.length == 1) return new ResetCommand(); 
		    	
		    	else if (commandWords.length > 1){
		    		
		    		String nivelString = commandWords[1]; 
		    		
		    	    if ("-1".equals(nivelString)) return new ResetCommand(-1);
		    	    if ("0".equals(nivelString))  return new ResetCommand(0);
		    	    if ("1".equals(nivelString))  return new ResetCommand(1);
		    	    
		    		
		    	}
	    	}
	    	return null; 
	    }
	    
		public void execute (GameModel game, GameView view) {

			if (this.numLevel == 1 || this.numLevel == 0 || this.numLevel == -1) {
				game.reset(numLevel); 
			}
			else {
				game.reset();
			}
			 
			view.showGame(); 
			
		}

	
}


