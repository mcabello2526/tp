package tp1.control.commands;

import tp1.view.Messages;
import tp1.logic.Action;
import java.util.*; 
import tp1.logic.Game;
import tp1.view.GameView;
import tp1.logic.GameModel;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
public class ActionCommand extends AbstractCommand{
	
	private List <Action> actions; // lista de acciones sin filtros
	
    private static final String NAME = Messages.COMMAND_ACTION_NAME;
    private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
    private static final String HELP = Messages.COMMAND_ACTION_HELP;
	
    public ActionCommand() {
    	super(NAME, SHORTCUT, DETAILS, HELP); 
    	actions = new ArrayList<Action>(); 
    }
    
    public ActionCommand (List <Action> act) {
    	super(NAME, SHORTCUT, DETAILS, HELP); 
    	actions = act; 
    }
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if (commandWords == null || commandWords.length == 0) return null; 
		
		if (commandWords.length > 1 && matchCommandName(commandWords[0])) {
			//entran acciones 
			for (int i = 1; i < commandWords.length; i++) {
				
				 String atrib = commandWords[i]; 
				 atrib.trim(); 
				 
				// recorrer los valores del enumerado por nombre entero
				 if (atrib.length() > 1) {
					 for (Action act: Action.values()) {  
						 if (atrib.equalsIgnoreCase(act.name())){
							 actions.add(act); 
						 }
					 } 
				 }
				 
				// recorrer los valores por caracter
				 else if (atrib.length() == 1) {
					 char c = Character.toLowerCase(atrib.charAt(0)); 
			            for (Action a : Action.values()) {
			                if (Character.toLowerCase(a.name().charAt(0)) == c) {
			                   actions.add(a); 
			                }
			            }
				 }
				 
				 
				
			}
			return new ActionCommand(actions); 
		}
		
		return null; 
	}
	
	@Override
	public void execute (GameModel game, GameView view) throws CommandExecuteException{
		// anadir a la lista de acciones de game para 
		// que la instancia de mario las procese
		for (Action a: actions) {
			game.addAction(a); 
		}
		
		game.update(); 
		view.showGame(); 
		
		// eliminar lista para procesar nuevas en el ciclo siguiente 
		actions.clear(); 
	}

	
}
