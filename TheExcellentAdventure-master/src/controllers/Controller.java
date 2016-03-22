package controllers;

import nodes.BasicNode;

import java.awt.event.KeyListener;

public interface Controller extends KeyListener{
	
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
	public void updateItem(BasicNode basicNode);
	
	/** 
	 * Appends a private message to the users text area
	 */
	public void privateMessage(String s);

	public void win();

	public void openPanel();

}
