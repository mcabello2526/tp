package tp1.control.commands;

import tp1.view.Messages;
import tp1.logic.Action;
import java.util.*; 
import tp1.logic.Game;
import tp1.view.GameView;
import tp1.logic.GameModel;
public class ActionCommand extends AbstractCommand{
	
	private List <Action> actions; 
	
    private static final String NAME = Messages.COMMAND_ACTION_NAME;
    private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
    private static final String HELP = Messages.COMMAND_ACTION_HELP;

/*
	public ActionCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		actions = new ArrayList<Action>();  
	}
*/	
    public ActionCommand() {
    	super(NAME, SHORTCUT, DETAILS, HELP); 
    	actions = new ArrayList<Action>(); 
    }
    
    public ActionCommand (List <Action> act) {
    	super(NAME, SHORTCUT, DETAILS, HELP); 
    	actions = act; 
    }
	
	
	public Command parse(String[] commandWords) {
		if (commandWords == null || commandWords.length == 0) return null; 
		if (commandWords.length > 1 && matchCommandName(commandWords[0])) {
			
			for (int i = 1; i < commandWords.length; i++) {
				
				 String token = commandWords[i]; 
				 token.trim(); 
				 
				 if (token.length() > 1) {
					 for (Action act: Action.values()) {
						 if (token.equalsIgnoreCase(act.name())){
							 actions.add(act); 
							 //return new ActionCommand(actions); 
						 }
					 } 
				 }
				
				 else if (token.length() == 1) {
					 char c = Character.toLowerCase(token.charAt(0)); 
			            for (Action a : Action.values()) {
			                if (Character.toLowerCase(a.name().charAt(0)) == c) {
			                   actions.add(a);
			                   //return new ActionCommand(actions); 
			                }
			            }
				 }
				 
				 
				
			}
			return new ActionCommand(actions); 
		}
		
		return null; 
	}
	
	
	public void execute (GameModel game, GameView view) {
		for (Action a: actions) {
			game.addAction(a); 
		}
		game.update(); 
		view.showGame(); 
		actions.clear(); 
		// view.showError(Messages.UNKNOWN_ACTION); 
	}

	
}
