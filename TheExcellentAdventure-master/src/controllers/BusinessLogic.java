package controllers;

import exceptions.GameLogicException;
import nodes.BasicNode;

/**
 * Business Logic is an interface that contains all the game logic in relation to what a player can do.
 * 
 * Namely;
 *  - turn 
 *  - move forward
 *  - drop an item
 *  - pick up an item
 *  - update an item
 * 
 * @author michaelwinton
 *
 */
public interface BusinessLogic {
	
	/**
	 * Turns a player left
	 * 
	 * @throws GameLogicException when the player turning left cannot be identified.
	 * @throws Exception
	 */
	public void turnLeft() throws GameLogicException;
	
	/**
	 * Turns a player right
	 * 
	 * @throws GameLogicException when the player turning right cannot be identified.
	 * @throws Exception
	 */
	public void turnRight() throws GameLogicException;
	
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
	public void goForward() throws GameLogicException;
	
	/**
	 * Drops an item from the players inventory
	 * 
	 * @param basicNode
	 */
	public void dropItem(BasicNode basicNode);
	
	/**
	 * Picks up an item and places it into the player's inventory
	 * @param basicNode
	 */
	public void pickUpItem(BasicNode basicNode);
	
	/**
	 * Finds the item in a room and updates it
	 * @param itemNode
	 */
	public void updateItem(BasicNode itemNode);
	
	public void businessWin();
	
	
	public void businessOpenPanel();
}
