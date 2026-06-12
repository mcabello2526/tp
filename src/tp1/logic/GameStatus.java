package tp1.logic;

public interface GameStatus {

	public String positionToString(int col, int row);
	
	public int points(); 
	public int numLives(); 
	public int remainingTime();
	
	public boolean playerWins(); 
	public boolean playerLoses(); 
	  
	public void resetTime(); 
}
