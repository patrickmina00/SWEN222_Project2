package gameworld;

import gameworld.items.Item;

import java.util.ArrayList;

/**
 * Represents a player within the gameworld.
 * Each Player can be differentiated with unique player ID.
 *
 * @author Daniel Tait
 *
 */

public class Player {

	//private Room currentRoom;
	private ArrayList<Item> items;
	private Orientation orientation;
	private int ID;
	private int roomId;
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	/**
	 * Constructs Player object and sets default Room and default direction of "NORTH"
	 *
	 * @param id - unique player identification
	 */

	public Player(int id, int rid, Orientation o){
		ID = id;
		//setCurrentRoom(r);
		roomId = rid;
		orientation = o;
		items = new ArrayList<Item>();
	}

	public int getID(){
		return ID;
	}

	public boolean equals(Player p){
		return this.ID == (p.getID());
	}

//	public Room getCurrentRoom() {
//		return currentRoom;
//	}
//
//	public void setCurrentRoom(Room cr) {
//		currentRoom = cr;
//	}


	public ArrayList<Item> getItems() {
		return items;
	}

	public void addItem(Item item) {
		this.items.add(item);
	}
	
	public void removeItem(Item item) {
		int index = 0;
		boolean found = false;
		for(index = 0; index < items.size(); index++){
			if(this.items.get(index).equalsID(item)){
				found = true;
				break;
			}
		}
		if(found){
			this.items.remove(index);
		} else {
			System.err.println("ERROR: Could not find index of item to remove!");
		}
		
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	@Override
	public String toString() {
		return "Player [currentRoom=" + roomId + ", items=" + items
				+ ", orientation=" + orientation + ", ID=" + ID + "]";
	}


}
