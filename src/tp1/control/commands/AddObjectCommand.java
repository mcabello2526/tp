package tp1.control.commands;

import tp1.view.Messages;
import tp1.view.GameView; 
import java.util.*;
import tp1.logic.GameModel;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;

public class AddObjectCommand extends AbstractCommand {
	
	private final String[] objWords; 
	
    private static final String NAME = Messages.COMMAND_AO_NAME;
    private static final String SHORTCUT = Messages.COMMAND_AO_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_AO_DETAILS;
    private static final String HELP = Messages.COMMAND_AO_HELP;
    
    public AddObjectCommand() {
    	super(NAME, SHORTCUT, DETAILS, HELP); 
    	objWords = null; 
    }
    
    public AddObjectCommand(String[] commandWords) {
    	super(NAME, SHORTCUT, DETAILS, HELP); 
    	objWords = commandWords; 
    }


    @Override 
    public Command parse(String[] commandWords) throws CommandParseException{
    	if (commandWords == null || commandWords.length == 0) return null;
    	if (!matchCommandName(commandWords[0])) return null;
    	
    
    	if (commandWords.length < 3) {
    		throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
    	}

    	String[] args = Arrays.copyOfRange(commandWords, 1, commandWords.length);
    	return new AddObjectCommand(args); 
    }
    
 
    @Override
    public void execute(GameModel game, GameView view) throws CommandExecuteException { 
        try {
           
            game.addObject(objWords);                    
            view.showGame();
            
        } catch (GameModelException e) {
           
            throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
        }
    }
}
