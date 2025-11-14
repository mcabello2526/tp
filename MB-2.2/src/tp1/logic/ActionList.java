package tp1.logic;

import java.util.ArrayList;
import java.util.List;

public class ActionList {
		
		private List<Action> actionList; 
		 
	   
	    private Action horizontalFirst;  // contadores verticales y horizontales para comprobar el array 
	    private int horizontalCount;
	    private Action verticalFirst;
	    private int verticalCount;
	    
	    private static final int MAX_REPEATS = 4;  // maximo de acciones seguidas

		
	    public ActionList() {  // constructora 
	        this.actionList = new ArrayList<Action>();
	        this.horizontalFirst = null;
	        this.horizontalCount = 0;
	        this.verticalFirst = null;
	        this.verticalCount = 0;
	    }

	    public void add(Action act) {
	        if (act == null) return;

	       
	        if (act == Action.LEFT || act == Action.RIGHT || act == Action.STOP) {  // horizontales 
	            if (horizontalFirst == null) {  // miramos si ya hay primera 
	                horizontalFirst = act;
	                horizontalCount = 1;
	                actionList.add(act);  
	            } else {
	                if (act == horizontalFirst && horizontalCount < MAX_REPEATS) { // si la hay la comparamos con la que entre 
	                    horizontalCount++;
	                    actionList.add(act);
	                } else {
	                   // ignoramos opuesta
	                }
	            }
	        } else { //verticales 
	            if (verticalFirst == null) {
	                verticalFirst = act;
	                verticalCount = 1;
	                actionList.add(act);
	            } else {
	                if (act == verticalFirst && verticalCount < MAX_REPEATS) {
	                    verticalCount++;
	                    actionList.add(act);
	                } else {
	                    // ignoramos opuesta 
	                }
	            }
	        }
	    }
	    

		
		public boolean applyRestriction(Action act1, Action act2) {
		    if (act1 == null || act2 == null) return false;
		    return (act1 == Action.LEFT  && act2 == Action.RIGHT) ||
		           (act1 == Action.RIGHT && act2 == Action.LEFT)  ||
		           (act1 == Action.UP    && act2 == Action.DOWN)  ||
		           (act1 == Action.DOWN  && act2 == Action.UP);
		}

	    public Action firstActionToDo() {  // nos devuelve la primera accion de la lista de acciones 
	        if (actionList.isEmpty()) return null;
	        return actionList.remove(0);
	    }
	    
	    
	    public void clear() {  // deletes 
	        actionList.clear();
	        horizontalFirst = null;
	        horizontalCount = 0;
	        verticalFirst = null;
	        verticalCount = 0;
	    }
}
