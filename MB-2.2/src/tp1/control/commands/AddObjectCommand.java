package tp1.control.commands;

import tp1.view.Messages;
import tp1.logic.GameWorld;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.view.GameView; 
import java.util.*;
import tp1.logic.gameobjects.GameObject; 
import tp1.logic.Game;
import tp1.logic.GameModel;

public class AddObjectCommand extends AbstractCommand {
	
	private String[] objWords; 
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
    public Command parse(String[] commandWords) {
    	
    	if (commandWords == null || commandWords.length == 0)return null; 
    	if (!matchCommandName(commandWords[0])) return null; 
    	if (commandWords.length < 2) return null; 
    	String[] args = Arrays.copyOfRange(commandWords, 1, commandWords.length); 
    	return new AddObjectCommand(args); 
    }

   public void execute(GameModel game, GameView view) { 
	   
    	if (objWords == null) {
    		view.showError(Messages.COMMAND_PARAMETERS_MISSING); 
    		return; 
    	}
    	 
    	boolean canAdd = game.addObjectFromString(objWords);  
    	
    		
    	if (!canAdd) {
    		String sObj = String.join(" ", objWords);
    		view.showError(Messages.INVALID_GAME_OBJECT.formatted(sObj));
    	}
    	else {
        	game.update(); 
        	view.showGame();
    	}
    }
   
   
}
