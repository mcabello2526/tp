package tp1.logic;

public interface GameStatus {

	public String positionToString(int col, int row);
	//TODO fill your code
	public int points(); 
	public int numLives(); 
	public int numLevel(); 
	
	
	public boolean playerWins(); 
	public boolean playerLoses(); 
	public int remainingTime(); 
	//public void exit(); 
	public void resetTime(); 
}
