package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.view.Messages;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
			new AddObjectCommand(),
			new ActionCommand(), 
			new UpdateCommand(), 
			new ResetCommand(), 
			new HelpCommand(),
			new ExitCommand() 		
	);

	public static Command parse(String[] commandWords) {	
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
		for (Command c: availableCommands) {
			commands.append(c.helpText());
		}
		return commands.toString();
	}

}
