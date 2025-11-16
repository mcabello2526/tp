package tp1.control.commands;

import tp1.logic.Game;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.GameModel; 
public class HelpCommand extends NoParamsCommand {

    private static final String NAME = Messages.COMMAND_HELP_NAME;
    private static final String SHORTCUT = Messages.COMMAND_HELP_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_HELP_DETAILS;
    private static final String HELP = Messages.COMMAND_HELP_HELP;

    public HelpCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
    
     
// **** MANERA 2 **********************************************************************************************

    @Override
    public Command parse (String[] commandWords) {
    	return super.parse(commandWords); 
    }

// ************************************************************************************************************
    
    // implement execute method
	@Override
	public void execute(GameModel game, GameView view) {
//      --> para que se necesita la instancia de game aqui ??
		view.showMessage(CommandGenerator.commandHelp());
	}
	

}
