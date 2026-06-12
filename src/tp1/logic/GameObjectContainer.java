package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {
	
	private final List<GameObject> objects; 
	private final List<GameObject> pending; 
	
	public GameObjectContainer() {
		objects = new ArrayList<GameObject>();
		pending = new ArrayList<GameObject>(); 
	}

// **** ADD DE OBJETOS Y ACCIONES ****************************************************************
	
	public void add(GameObject object) {
		if (object != null) objects.add(object); 
	}
	
	public void addToPending(GameObject object) {
		if (object != null) pending.add(object); 
	}

	public void addAction(Action a) {
	    for(GameObject o : objects) { o.performUserAction(a); }  // DESARROLLADA EN MARIO 
	}

	private void removeDead() {
		// creamos una lista de los objetos muertos en cada ciclo del update() 
		// y los limpiamos todos juntos
		
		ArrayList<GameObject> toRemove = new ArrayList<>();
		for (GameObject gameO : objects) {
			if (!gameO.isAlive()) toRemove.add(gameO); 
		}
		if (!toRemove.isEmpty()) {
			objects.removeAll(toRemove); 
		}
	}
	
// **** LOGICA PRINCIPAL DEL CONTENEDOR ************************************************************
	
	public void doInteraction(GameItem other) {
		for (GameObject object : objects) {
			
			// si alguno esta muerto pasamos al siguiente 
			if (!object.isAlive() || !other.isAlive()) continue;
			
			object.interactWith(other);							
			other.interactWith(object); 	 
		}	
	}
		
		
	public void update() {
		int maxPriorities = 3; 

		// vamos haciendo el update segun las prioridades de 1(Mario), 2(Goombas y Mushroom)  a 3(Land) 
	    for (int currentPriority = 1; currentPriority <= maxPriorities; currentPriority++) {
	        for (GameObject o : objects) {
	            if (o.getUpdatePriority() == currentPriority) {
	                o.update();
	            }
	        }
	    }
	    
	    removeDead();
	    if (!pending.isEmpty()) {
	        objects.addAll(pending);
	        pending.clear();
	    }
	}
	
// **** ENCAPSULACION ********************************************************************************
	
	public boolean isSolid(Position pos) {
		for (GameObject o: objects) {
			if (o.isInPosition(pos)) {
				return o.isSolid(); 
			}
		}
		return false; 
	}
	
// **** RESPRESENTACION DE OBJETOS *******************************************************************
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(GameObject object : objects) {
			str.append(object.toString()).append(System.lineSeparator());
		}
		return str.toString();
	}
	
	
	public String positionToString(Position position) {
		StringBuilder auxString = new StringBuilder();
		for (GameObject object : objects) {
		    if (object.isInPosition(position)) {
		        auxString.append(object.getIcon());
		    }
		}
		
		if (auxString.length() > 0) {
			return auxString.toString(); 
		}
		
		return ""; 
	}
	

}















































































































