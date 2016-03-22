package gameworld.rooms;

import gameworld.Player;
import gameworld.items.Item;
import gameworld.logic.PlayerConstraint;
import gameworld.logic.RoomConstraint;

import java.util.ArrayList;

public class Room {

	protected ArrayList<RoomConstraint> roomConstraint = new ArrayList<RoomConstraint>();
	protected ArrayList<PlayerConstraint> playerConstraint = new ArrayList<PlayerConstraint>();
	
	private ArrayList<Item> items = new ArrayList<Item>();
	private String name;
	protected int id = -1;
	
	protected Room north = null;
	protected Room east = null;
	protected Room south = null;
	protected Room west = null;
	
	
	public int getId(){
		return id;
	}
	
	/**
	 * A method that checks if the player can move from the current room they are in
	 * to the room they want to move to in their current state and the rooms
	 * current state.
	 * @param current the room the player is in.
	 * @param moveTo the room the player is trying to move to.
	 * @param p the player.
	 * @return the room that the player can move to. Null if they cannot.
	 */
	protected Room checkSuccesfulMove(Room current, Room moveTo, Player p){
		if(moveTo == null){
			//Can't move into a wall
			return null;
		}
		
		System.out.println("Trying to move from " + current.toString() + " to " + moveTo.toString());
		
		boolean succesfulMove = this.checkMove(current, p);
		if(succesfulMove){
			return moveTo;
		} else {
			return null;
		}
		
	}
	
	
	/**
	 * A method that switches through the different orientations of the player
	 * and checks if there is a room that can be moved into in front of them.
	 * @param p the player to move.
	 * @return A room if it is possible to move in that direction or null if not.
	 */
	public Room leaveRoomWithOrientation(Player p){

		//Check if there is a room we can go to
		
		switch(p.getOrientation()){
		case EAST: return checkSuccesfulMove(this, east, p);
		case NORTH:return checkSuccesfulMove(this, north, p);
		case SOUTH:return checkSuccesfulMove(this, south, p);
		case WEST: return checkSuccesfulMove(this, west, p);
		default:
			return null;
		}
	}
	
	/**
	 * A method that tests all the contraints of a player and their direction as they try to move 
	 * into the next room.
	 * @param p the player on the room
	 * @return a boolean if the player passes all the constraints.
	 */
	public boolean canLeaveRoom(Player p){
		//check orientation and see if that way has any constraints... if so:		
		//check that we have fulfilled the rooms requirements to move to the next room
		//using RoomConstraint
		for(RoomConstraint rConstraint : this.roomConstraint ){			
			//check only the constraints of the direction that we are going in
			if(rConstraint.getOrientation() == p.getOrientation()){
				//if we fail a constraint return false
				if(!rConstraint.passesConstraint(this)){					
					return false;
					
				}
				
			}
			//Test individual player constraints too.
		}for(PlayerConstraint pConstraint : this.playerConstraint){
			if(pConstraint.getOrientation() == p.getOrientation()){
				//if we fail a constraint return false
				if(!pConstraint.passesConstraint(p)){	
					//If the player fails any of the constraints return false.
					return false;					
				}				
			}
		}
		return true;
	}
	
	/**
	 * A method which checks if a player p can leave room r in their current orientation.
	 * @param r the room the current player is in.
	 * @param p the player.
	 * @return a boolean field if the contraints pass and if there is a room in that direction.
	 */
	public boolean checkMove(Room r, Player p){
		if(r != null) {
			return r.canLeaveRoom(p);
		}
		return false;
	}
	
	public void addItem(Item i){
		items.add(i);
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	@Override
	public String toString() {
		return "Room [items=" + items + ", name=" + name + "]";
	}

	public Room getNorth() {
		return north;
	}

	public void setNorth(Room north) {
		this.north = north;
	}

	public Room getEast() {
		return east;
	}

	public void setEast(Room east) {
		this.east = east;
	}

	public Room getSouth() {
		return south;
	}

	public void setSouth(Room south) {
		this.south = south;
	}

	public Room getWest() {
		return west;
	}

	public void setWest(Room west) {
		this.west = west;
	}

	public void updateItem(Item item) {
		int index = 0;
		for(; index < items.size(); index++){
			if(item.equalsID(items.get(index))){
				break;
			}
		}
		
		Item i = items.remove(index);
		System.out.println("Removed item with state: " + i.getState());
		items.add(item);
		System.out.println("Added item with state: " + item.getState());
		
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public void removeItem(Item item){
		int index = 0;
		for(; index < items.size(); index++){
			if(item.equalsID(items.get(index))){
				break;
			}
		}
		
		items.remove(index);
	}
	
}
