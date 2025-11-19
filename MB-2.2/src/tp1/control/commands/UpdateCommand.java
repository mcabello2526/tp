package tp1.control.commands;

import tp1.logic.Game;
import tp1.view.GameView; 
import tp1.view.Messages; 
import tp1.logic.GameModel; 
public class UpdateCommand extends NoParamsCommand {
	
	private static final String NAME = Messages.COMMAND_UPDATE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
	private static final String HELP = Messages.COMMAND_UPDATE_HELP;

	public UpdateCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP); 
	}
	

    @Override
    public Command parse (String[] commandWords) {
        if (commandWords == null) {
            return null;
        }

        if (commandWords.length == 0 || (commandWords.length == 1 && commandWords[0].isEmpty())) {
            return this;
        }

        return super.parse(commandWords); 
    }


	@Override
	public void execute (GameModel game, GameView view) {
		// hace el update del juego 
		game.update(); 
		view.showGame();
	}
	
	// falta helpText()

}
