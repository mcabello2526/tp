package tp1.logic;



import java.util.Objects;

import tp1.exceptions.PositionParseException;
import tp1.view.Messages;

public class Position {

	private final int col;
	private final int row;
	
	
	public Position(int col, int row) {
		this.row = row; 
		this.col = col; 
	}
	
	public int getRow() {
		return row; 
	}
	
	public int getCol() {
		return col; 
	}


	
//*****
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
	
//**** ASIGNACION **********************************************************************************************

	public Position assignPos(Action act) {
		return new Position(col + act.getY(), row + act.getX()); 
	}

	public Position abajo() {
		return new Position(col +1 , row);
	}

	public Position arriba() {
		return new Position(col -1 , row);
	}
	
	public Position copy() {
	    return new Position(this.col, this.row); 
	}

	public boolean posValida() {   
		return ((this.col >= 0 && this.col < Game.DIM_Y) && (this.row >= 0 && this.row < Game.DIM_X));
	}
	
// **** PARSE **************************************************************************************************
	
	public static Position parse(String pos) throws PositionParseException {
		
		//comprueba si la posicion no es nula 
	    if (pos == null) throw new PositionParseException(Messages.INVALID_POSITION.formatted("null"));
	    pos = pos.trim();
	    if (!pos.startsWith("(") || !pos.endsWith(")")) {
	        throw new PositionParseException(Messages.INVALID_POSITION.formatted(pos));
	    }
	    String inner = pos.substring(1, pos.length() - 1);
	    
	    // creamos un array para las posiciones
	    String[] parts = inner.split(",");
	    if (parts.length != 2) throw new PositionParseException(Messages.INVALID_POSITION.formatted(pos));

	    try {
	    	// intentamos hacer el parse de los numeros enteros de las posiciones
	        int c = Integer.parseInt(parts[0].trim());
	        int r = Integer.parseInt(parts[1].trim());
	        	    
	        return new Position(c, r);
	        
	    } catch (NumberFormatException e) {
	        throw new PositionParseException(Messages.INVALID_POSITION.formatted(pos), e);
	    }
	}
	
//**** VISTA TEXTUAL ********************************************************************************************
	
	public String toString() {
		return "(" + col + "," + row + ")";
	}
	

}
