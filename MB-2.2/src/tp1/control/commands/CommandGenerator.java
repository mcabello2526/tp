package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.view.Messages;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
			new ResetCommand(), 
			new ActionCommand(), 
			new HelpCommand(),
			new ExitCommand(), 
			new UpdateCommand(), 
			new AddObjectCommand()
	);

	public static Command parse(String[] commandWords) {	// aqui hay que hacer las nuevas instancias?
		//if (commandWords == null || commandWords.length == 0) return null;
        for (Command c: availableCommands) {
            Command comando = c.parse(commandWords);
            if (comando!= null) {
                return comando; 
            }
        }
        
        return null;
	}
	
		
	public static String commandHelp() {
	    StringBuilder commands = new StringBuilder();
	    
	    commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
	    
	    for (Command c: availableCommands) {
	        commands.append(c.helpText()).append(Messages.LINE_SEPARATOR);
	    }
	    
	    return commands.toString();
	}

}
