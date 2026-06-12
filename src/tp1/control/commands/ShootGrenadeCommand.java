package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ShootGrenadeCommand extends NoParamsCommand{
	
	private static final String NAME = Messages.COMMAND_GRENADE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_GRENADE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_GRENADE_DETAILS;
	private static final String HELP = Messages.COMMAND_GRENADE_HELP;
	
	public ShootGrenadeCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

    @Override
    public Command parse (String[] commandWords)throws CommandParseException {
        return super.parse(commandWords); 
    }
    
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		//game.shootGrenade(); 
		view.showGame(); 
		
	}


}
