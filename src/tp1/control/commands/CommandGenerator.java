package tp1.control.commands;

import java.util.Arrays;
import java.util.List;
import tp1.view.Messages;
import tp1.exceptions.CommandParseException;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
			new LoadCommand(),
			new SaveCommand(),
			new AddObjectCommand(),
			new ActionCommand(), 
			new UpdateCommand(), 
			new ResetCommand(), 
			new HelpCommand(),
			new ExitCommand(), 
			new ShootGrenadeCommand()
	);

	public static Command parse(String[] commandWords) throws CommandParseException {	
        for (Command c: availableCommands) {
        
            Command comando = c.parse(commandWords);
            if (comando!= null) {
                return comando; 
            }
        }
        
        throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(commandWords[0]));
	}
	

	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();
		
		for (Command c: availableCommands) {
			commands.append(c.helpText());
		}
		return commands.toString();
	}

}
