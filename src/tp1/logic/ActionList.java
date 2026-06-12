package tp1.logic;

import java.util.ArrayList;
import java.util.List;

public class ActionList {
    	private static final int MAX_REPEATS = 4;
		
		private final List<Action> actionList; 
		 
	   
	    private Action horizontalFirst;  
	    private int horizontalCount;
	    private Action verticalFirst;
	    private int verticalCount;
	    
  

		
	    public ActionList() {  
	        this.actionList = new ArrayList<Action>();
	        this.horizontalFirst = null;
	        this.horizontalCount = 0;
	        this.verticalFirst = null;
	        this.verticalCount = 0;
	    }

	    public void add(Action act) {
	        if (act == null) return;

	       // entrada acciones horizontales 
	        if (act == Action.LEFT || act == Action.RIGHT || act == Action.STOP) {  
	            if (horizontalFirst == null) {  
	                horizontalFirst = act;
	                horizontalCount = 1;
	                actionList.add(act);  
	            } else {
	            	// si es la primera horizontal y es menor que el numero de repeticiones 
	                if (act == horizontalFirst && horizontalCount < MAX_REPEATS) { 
	                    horizontalCount++;
	                    actionList.add(act);
	                } else {
	                   // si ya habia una horizontal no hace nada 
	                }
	            }
	        } else { 
	        	// entrada de acciones verticales 
	            if (verticalFirst == null) {
	                verticalFirst = act;
	                verticalCount = 1;
	                actionList.add(act);
	            } else {
	            	// si es la primera y hay menos del numero total
	                if (act == verticalFirst && verticalCount < MAX_REPEATS) {
	                    verticalCount++;
	                    actionList.add(act);
	                } else {
	                    // si no es la primera es porque ya habia antes una vertical y no es igual a ella, 
	                	// es la opuesta y la obviamos
	                }
	            }
	        }
	    }
	    	    

	    public Action firstActionToDo() {  
	        if (actionList.isEmpty()) return null;
	        return actionList.remove(0);
	    }
	    

	    public void clear() {   
	        actionList.clear();
	        horizontalFirst = null;
	        horizontalCount = 0;
	        verticalFirst = null;
	        verticalCount = 0;
	    }
}
