package tp1.logic;


/**
 * 
 * TODO: Immutable class to encapsulate and manipulate positions in the game board
 * 
 */


import java.util.Objects;

public class Position {

	private int col;
	private int row;
	
	
	public Position(int col, int row) {
		this.row = row; 
		this.col = col; 
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return col == other.col && row == other.row;
	}


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
		default:
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
