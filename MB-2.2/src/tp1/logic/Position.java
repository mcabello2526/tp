package tp1.logic;


/**
 * 
 * TODO: Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
import tp1.logic.Game;
import tp1.logic.Action;

public class Position {

	private int col;
	private int row;
	
	private int DIM = 30*15;                        // no estoy segura de lo que tiene que ser DIM 
	
	public Position(int col, int row) {
		this.row = row; 
		this.col = col; 
	}
	// nuevo 
	@Override
	/*public int hashCode() {
		return this.row * DIM + this.col; 
	}
	*/

	public boolean equals(Object o) {       		 // metodo equals sobreescrito para comprobar si dos posiciones son iguales 
        if (this == o) return true;
        if (!(o instanceof Position)) return false;  // el instance of comprueba si es de la misma clase 
        Position p = (Position) o;
        //return this.hashCode() == o.hashCode(); 
        return row == p.row && col == p.col;
    }
	
// nuevo
	public Position assignPos(Action act) {
		Position pos = null;
		
		switch (act){
		case Action.LEFT: 
			 pos = new Position (col, row + act.getX()); 
		break; 
		
		case Action.RIGHT: 
			pos =  new Position (col, row + act.getX()); 
		break; 
		case Action.UP: 
			pos =  new Position (col + act.getY(), row ); 
		break; 
			
		case Action.DOWN: 
			pos = new Position (col + act.getY(), row );
		break; 
		}
		
		return pos; 
	}

	public Position abajo() {
		return new Position(col +1 , row);
	}

	public Position arriba() {
		return new Position(col -1 , row);
	}
	
	public Position derecha() {
		return new Position(col, row +1);
	}
	
	public Position izquierda() {
		return new Position(col, row -1);
	}
	

	public boolean posValida() {     //comprueba si la posicion es valida y en dicho caso devuelve true
		return ((this.col >= 0 && this.col < Game.DIM_Y) && (this.row >= 0 && this.row < Game.DIM_X));
	}
	
	public String toString() {
		return "(" + row + "," + col + ")";
	}
}
