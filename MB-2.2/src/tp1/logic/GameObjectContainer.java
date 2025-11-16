package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {
	private List<GameObject> objects; 
	private List<GameObject> pending; 
	
	public GameObjectContainer() {
		objects = new ArrayList<GameObject>();
		pending = new ArrayList<GameObject>(); 
	}
	
	public void add(GameObject object) {
		if (object != null) objects.add(object); 
	}
	
	public void addToPending(GameObject object) {
		if (object != null) pending.add(object); 
	}
	
	public String postitionToString(Position pos) {
		if (pos != null) return pos.toString(); // el del override de abajo 
		return ""; 
	}

	public void removeDead() {
		ArrayList<GameObject> toRemove = new ArrayList<>();
		for (GameObject gameO : objects) {
			if (!gameO.isAlive()) toRemove.add(gameO); 
		}
		if (!toRemove.isEmpty()) {
			objects.removeAll(toRemove); 
		}
	}
	
	public void doInteraction(GameItem other) {
		for (GameObject object : objects) {
			object.interactWith(other);
			other.interactWith(object); 
		}
		
	}
	

	public String getIcon (Position pos) {
		StringBuilder icons = new StringBuilder(); 
	
		for (GameObject o: objects) {
			if (o.isInPosition(pos)) {
				 icons.append(o.getIcon()); 
			}
				
		}
			
		if (icons.length()> 0) {
			return icons.toString(); 
			
		}
		
		return ""; 
		
	}
	
	public boolean isSolid(Position pos) {
		for (GameObject o: objects) {
			if (o.isInPosition(pos)) {
				return o.isSolid(); 
			}
		}
		return false; 
	}
	
	public void update() {
		for (GameObject o: objects) {
			o.update();
			doInteraction(o); 
		}
		removeDead(); 
		
		if (!pending.isEmpty()) {
			objects.addAll(pending); 
			pending.clear();
		}
	}
	
	// TODO you should write a toString method to return the string that represents the object status
	// @Override
	// public String toString()
	
/*
	public boolean belowHit (Position pos, GameItem item) {
		  if (pos == null || item == null) return false;
		  
		    for (GameObject o : objects) {
		        if (o.isInPosition(pos)) {
		            return o.receiveInteraction(item); 
		        }
		    }
		    return false;
	}
*/

/*
	public boolean checkInContainer(GameObject obj) {
		if (obj == null) return false;
		for (GameObject o: objects) {
			if (o.equals(obj)) {
				return true; 
			}
		}
		return false; 
	}
*/	

	
}















































































































