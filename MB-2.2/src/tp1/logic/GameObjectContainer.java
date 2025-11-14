package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {
	private List<GameObject> objects;

	public GameObjectContainer() {
		objects = new ArrayList<GameObject>();
	}
	
	// Only one add method (polymorphism)
	
	public void add(GameObject object) {
		if (object != null) objects.add(object); 
	}
	
	public String postitionToString(Position pos) {
		//TODO fill your code
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
		for (GameObject o: objects) {
			if (o.isInPosition(pos)) {
				return o.getIcon(); 
			}
			
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
	}
	
	// TODO you should write a toString method to return the string that represents the object status
	// @Override
	// public String toString()
	
/*
	public boolean belowHit (Position pos, GameObject obj) {
		  if (pos == null || obj == null) return false;
		    for (GameObject o : objects) {
		        if (o.isInPosition(pos)) {
		            return o.receiveInteraction(obj); // polimorfismo puro
		        }
		    }
		    return false;
	}
*/
}















































































































