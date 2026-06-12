package tp1.control.commands;



import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends AbstractCommand{
	private final String filename;
	
    private static final String NAME = Messages.COMMAND_SAVE_NAME;
    private static final String SHORTCUT = Messages.COMMAND_SAVE_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_SAVE_DETAILS;
    private static final String HELP = Messages.COMMAND_SAVE_HELP;
    
    public SaveCommand() {
    	super(NAME, SHORTCUT, DETAILS, HELP);
        this.filename = null;
    }
    
    public SaveCommand(String filename) {
        super(NAME, SHORTCUT, DETAILS, HELP);
        this.filename = filename;
    }
    
    @Override
    public Command parse(String[] commandWords) throws CommandParseException {
        if (!matchCommandName(commandWords[0])) return null;
        if (commandWords.length != 2) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
        return new SaveCommand(commandWords[1]);
    }
    
    @Override
    public void execute(GameModel game, GameView view) throws CommandExecuteException {
        try {
            
            game.save(filename);
            view.showMessage("Game successfully saved to file " + filename + ".");
            
        } catch (GameModelException e) {
            throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
        }
    }
    
}
