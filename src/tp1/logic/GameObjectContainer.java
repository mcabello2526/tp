package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.view.Messages; 
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


	private void removeDead() {
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
		}
		
		removeDead();
		
		if (!pending.isEmpty()) {
			objects.addAll(pending); 
			pending.clear();
		}	

		
	}	
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(GameObject object : objects) {
			//str.append(Messages.LINE.formatted(object));
			str.append(object.toString()).append(System.lineSeparator());
		}
		return str.toString();
	}	
}















































































































