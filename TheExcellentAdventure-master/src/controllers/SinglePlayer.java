package controllers;

import gameworld.Orientation;
import gameworld.Player;
import gameworld.items.Item;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.items.Type;
import gameworld.rooms.Room;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import nodes.*;
import datastorage.Game;
import exceptions.GameLogicException;

/**
 * SinglePlayer is the controller for BallOut when in the SinglePlayer mode
 * 
 * It's primary function is to handle player input and then act accordingly
 * 
 * @author michaelwinton
 *
 */
public class SinglePlayer implements KeyListener, Controller, BusinessLogic {

	private Game game;
	private final int uid;
	private ItemManager itemManager;

	public SinglePlayer(Game game, int uid) {
		this.game = game;
		this.uid = uid;
		this.itemManager = new ItemManagerImpl();
	}

	/**
	 *  The following intercept keyboard events from the user.
	 */
	public void keyPressed(KeyEvent e) {	
		try {

			int code = e.getKeyCode();

			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {			
				this.turnRight();
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				this.turnLeft();
			} else if(code == KeyEvent.VK_UP) {
				this.goForward();
			} 
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void keyReleased(KeyEvent e) {		
	}

	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Turns a player left
	 * 
	 * @throws GameLogicException when the player turning left cannot be identified.
	 * @throws Exception
	 */
	public void turnLeft() throws GameLogicException{
		switch(game.player(uid).getOrientation()){
		case EAST:
			game.player(uid).setOrientation(Orientation.NORTH);
			break;
		case NORTH:
			game.player(uid).setOrientation(Orientation.WEST);
			break;
		case SOUTH:
			game.player(uid).setOrientation(Orientation.EAST);
			break;
		case WEST:
			game.player(uid).setOrientation(Orientation.SOUTH);
			break;
		default:
			System.err.println("ERROR: Could not turnLeft");
			break;
		}
	}

	/**
	 * Makes a player attempt to go forward.
	 * 
	 * This will work if;
	 * - the player passes the current room's room constraints
	 * - the player passes the current room's player constraints
	 * - the player is not attempting to walk into a wall
	 * 
	 * @throws GameLogicException when the player turning left cannot be identified.
	 * @throws Exception
	 */
	public void goForward() throws GameLogicException{

		//Locates the players current room
		Room currentRoom = game.getRooms().get(game.player(uid).getRoomId());
		
		//Checks if we can move forward, returns the room we can move to on success, or null on failure
		Room moveToRoom = currentRoom.leaveRoomWithOrientation(game.player(uid));

		//if successful
		if(moveToRoom != null){
			System.out.println("Moved to room: " + moveToRoom.getId());
			
			//move the player to the new room
			game.player(uid).setRoomId(moveToRoom.getId());
			
		} else {
			
			Player p = game.player(uid);
			
			Room nextRoom = this.checkIfPlayerWasTryingToMoveIntoARoom(p,currentRoom);
			
			if(nextRoom != null){
				
				this.privateMessage("You try move into the next room, but fail... maybe you need try another approach...");
				
			} else {
				this.privateMessage("You try move forward, but fail!");
			}
			
			
		}
	}
	
	private Room checkIfPlayerWasTryingToMoveIntoARoom(Player p, Room currentRoom){
		
		if(p.getOrientation() == Orientation.NORTH){
			
			if(currentRoom.getNorth() != null){
				return currentRoom.getNorth();
			}
			
		} else if(p.getOrientation() == Orientation.EAST){
			
			if(currentRoom.getEast() != null){
				return currentRoom.getEast();
			}
			
		} else if(p.getOrientation() == Orientation.SOUTH){
			
			if(currentRoom.getSouth() != null){
				return currentRoom.getSouth();
			}
			
		} else if(p.getOrientation() == Orientation.WEST){
			
			if(currentRoom.getWest() != null){
				return currentRoom.getWest();
			}
			
		} 
		
		return null;
		
	}

	/**
	 * Turns a player right
	 * 
	 * @throws GameLogicException when the player turning right cannot be identified.
	 * @throws Exception
	 */
	public void turnRight() throws GameLogicException{
		switch(game.player(uid).getOrientation()){
		case EAST:
			game.player(uid).setOrientation(Orientation.SOUTH);
			break;
		case NORTH:
			game.player(uid).setOrientation(Orientation.EAST);
			break;
		case SOUTH:
			game.player(uid).setOrientation(Orientation.WEST);
			break;
		case WEST:
			game.player(uid).setOrientation(Orientation.NORTH);
			break;
		default:
			System.err.println("ERROR: Could not turnRight");
			break;
		}
	}

	/**
	 * Drops an item from the players inventory
	 * 
	 * @param basicNode
	 */
	@Override
	public void dropItem(BasicNode itemNode) {	
		
		//finds the player who is dropping the item
		for (Player p : game.getPlayers()){
			if(p.getID() == uid){
				
				//removes the item from player and adds it to their current room
				Item i = itemManager.getItemWithIdAndState(itemNode.getId(), itemNode.getState());
				System.out.println(itemNode.getState());
				p.removeItem(i);
				game.getRooms().get(p.getRoomId()).addItem(i);
			}
		}
		
	}

	/**
	 * Picks up an item and places it into the player's inventory
	 * @param basicNode
	 */
	@Override
	public void pickUpItem(BasicNode itemNode) {
		
		//finds the player who is picking up the item
		for (Player p : game.getPlayers()){
			if(p.getID() == uid){
				
				//removes the item from room and adds it to the player
				Item item = itemManager.getItemWithIdAndState(itemNode.getId(), itemNode.getState());
				game.getRooms().get(p.getRoomId()).removeItem(item);
				p.addItem(item);
			}
		}
		
	}

	/**
	 * Finds the item in a room and updates it
	 * @param itemNode
	 */
	@Override
	public void updateItem(BasicNode itemNode) {
		Item item = itemManager.getItemWithIdAndState(itemNode.getId(), itemNode.getState());
		
		int rid = -1;
		
		for(Room r: game.getRooms().values()){
			for(Item i: r.getItems()){
				if(i.equalsID(item)){
					rid = r.getId();
				}
			}
		}
		
		if(rid == -1){
			System.err.println("Could not update item");
		} else {
			Room r = game.getRooms().get(rid);
			System.out.println("Singleplayer state: " + item.getState());
			r.updateItem(item);
		}
	}
	
	@Override
	public void privateMessage(String s){
		System.out.println("Setting message: " + s);
		game.setMessage(uid + " "+s);
	}

	
	public List<Item> playerInventory() {
		for(Player p: game.getPlayers()){
			if(p.getID() == uid){
				return p.getItems();
			}
		}
		System.out.println("Could not find player.");
		return null;
	}

	@Override
	public void win() {
		businessWin();		
	}

	@Override
	public void businessWin() {
		System.out.println("you win");
		game.setGameRunning(false);		//end game!
	}

	@Override
	public void businessOpenPanel() {
		
		boolean found = false;
		
		for(Item it: playerInventory()){
			if(it.getID() == 9){
				found = true;
			}
		}
		
		if(found){
			ItemNode pan = new ItemNode(10, ItemState.FALSE, Type.ANCHORED);	
			this.updateItem(pan);
			this.privateMessage("You pry the panel open with your screwdriver exposing some loose wires.\n");
		} else {
			this.privateMessage("This panel looks loose, you might me able to pry it open somehow...\n");
		}
	}

	@Override
	public void openPanel() {
		businessOpenPanel();
	}
}
