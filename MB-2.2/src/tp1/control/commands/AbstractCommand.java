package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;


// clase abstracta --> metodos incompleos de interfaz Command
public abstract class AbstractCommand implements Command {

	private final String name;
	private final String shorcut;
	private final String details;
	private final String help;
	
	public AbstractCommand(String name, String shorcut, String details, String help) {
		this.name = name;
		this.shorcut = shorcut;
		this.details = details;
		this.help = help;
	}

	
	protected String getName() { return name; }
	protected String getShortcut() { return shorcut; }
	protected String getDetails() { return details; }
	protected String getHelp() { return help; }
	
	//comprobar por nombre y por abreviatura 
	protected boolean matchCommandName(String name) {
		return getShortcut().equalsIgnoreCase(name) || 
			   getName().equalsIgnoreCase(name);
	}
	
	@Override
	public abstract void execute(GameModel game, GameView view);
	
	@Override
	public abstract Command parse(String[] commandWords); 

	@Override
	public String helpText(){
		// formatted --> cabecera: definicion 
		return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp()));
	}
	
	@Override 
	public String toString() {
		return helpText(); 
	}
}
