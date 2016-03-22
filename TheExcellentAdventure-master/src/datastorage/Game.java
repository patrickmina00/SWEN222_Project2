package datastorage;

import exceptions.GameLogicException;
import gameworld.Orientation;
import gameworld.Player;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.rooms.*;

import java.util.*;

import parser.GsParser;


/** Represents the game BallOut
 * 
 * @author Daniel Tait
 *
 */

public class Game {

	private HashMap<Integer, Room> rooms;
	private ArrayList<Player> players;
	private GameMode mode;
	private GameState gameState = null;
	private String message;
	private boolean gameRunning;
	
	public enum GameMode{
		SINGLE, MULTIPLAYER;
	}

	public Game(GameMode g){
		
		this.mode = g;
		players = new ArrayList<Player>();
		this.generateRooms();
		gameRunning = true;
	}
	
	/**
	 * Creates an instance of game and then given the rooms, connects them up
	 * 
	 * @param g
	 * @param players
	 * @param rooms
	 */
	public Game(GameMode g, ArrayList<Player> players, HashMap<Integer, Room> rooms){
		
		this.mode = g;
		this.players = players;
		this.rooms = rooms;
		this.gameRunning = true;
		
		this.connectRooms();
		
	}
	
	/**
	 * Registers a player adding them to the game at the Starting Area face north
	 * 
	 * @return Player
	 */
	public Player registerPlayer(){
		
		int playerID = players.size();//ensures we are getting a unique ID
		Player player = new Player(playerID, StartingArea.rid, Orientation.NORTH);
		
		players.add(player);
		return player;
	}

	public HashMap<Integer, Room> getRooms() {
		return rooms;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public GameMode getMode() {
		return mode;
	}
	
	public boolean isGameRunning(){
		return gameRunning;
	}
	
	public void setGameRunning(boolean gr){
		this.gameRunning = gr;
	}
	/**
	 * Given a player uid, we attempt to return the player
	 * @param uid
	 * @return
	 * @throws GameLogicException
	 */
	public Player player(int uid) throws GameLogicException{
		for(Player p: players){
			if(p.getID() == uid){
				return p;
			}
		}
		throw new GameLogicException("Player not found with ID: "+ uid);
	}
	
	/**
	 * Method that creates the rooms initialises the items in them
	 * and then calls the connect method to link them up.
	 */
	private void generateRooms() {
		
		ItemManager itemManager = new ItemManagerImpl();
		
		rooms = new HashMap<Integer, Room>();
		
		StartingArea startingArea = new StartingArea();
		startingArea.addItem(itemManager.getItemWithIdAndInitialState(0));
		rooms.put(StartingArea.rid, startingArea);
		
		LeverRoom leverRoom = new LeverRoom();
		leverRoom.addItem(itemManager.getItemWithIdAndInitialState(1));
		leverRoom.addItem(itemManager.getItemWithIdAndInitialState(2));
		leverRoom.addItem(itemManager.getItemWithIdAndInitialState(3));
		rooms.put(LeverRoom.rid, leverRoom);
		
		GenericRoom1 genRoom1 = new GenericRoom1();
		rooms.put(GenericRoom1.rid, genRoom1);
		
		LockerRoom lockerRoom = new LockerRoom();
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(5)); //Locker
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(6)); //Rad suit
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(13)); //Chair
		lockerRoom.addItem(itemManager.getItemWithIdAndInitialState(4));//Torch
		rooms.put(LockerRoom.rid, lockerRoom);
		
		DarkRoom darkRoom = new DarkRoom();
		rooms.put(DarkRoom.rid, darkRoom);
		
		Toilet toilet = new Toilet();
		toilet.addItem(itemManager.getItemWithIdAndInitialState(8));//sink
		toilet.addItem(itemManager.getItemWithIdAndInitialState(7));//toilet
		toilet.addItem(itemManager.getItemWithIdAndInitialState(12));//key (not visible)
		rooms.put(Toilet.rid, toilet);
		
		GenericRoom2 genRoom2 = new GenericRoom2();
		rooms.put(GenericRoom2.rid, genRoom2);
		
		IrradiatedRoom irradiatedRoom = new IrradiatedRoom();
		irradiatedRoom.addItem(itemManager.getItemWithIdAndInitialState(9));//screwdriver
		rooms.put(IrradiatedRoom.rid, irradiatedRoom);
		
		Exit exit = new Exit();
		exit.addItem(itemManager.getItemWithIdAndInitialState(10));//Panel
		exit.addItem(itemManager.getItemWithIdAndInitialState(11));//wires
		rooms.put(Exit.rid, exit);
		
		
		this.connectRooms();
	}
	
	/**
	 * Connects the rooms, so that a user can move through each room
	 * 
	 * For each "Door" we are connecting there must be two settings, e.g.
	 * 
	 * RoomA.setNorth(RoomB);
	 * RoomB.setSouth(RoomA);
	 * 
	 * or
	 * 
	 * RoomA.setEast(RoomB);
	 * RoomB.setWest(RoomA);
	 * 
	 */
	private void connectRooms(){
		Room startingArea = rooms.get(StartingArea.rid);		//0
		Room leverRoom = rooms.get(LeverRoom.rid);				//1
		Room genericRoom1 = rooms.get(GenericRoom1.rid);		//2
		Room lockerRoom = rooms.get(LockerRoom.rid);			//3
		Room darkRoom = rooms.get(DarkRoom.rid);				//4
		Room toilet = rooms.get(Toilet.rid);					//5
		Room genericRoom2 = rooms.get(GenericRoom2.rid);		//6
		Room irradiatedRoom = rooms.get(IrradiatedRoom.rid);	//7
		Room exit = rooms.get(Exit.rid);						//8
		
		startingArea.setNorth(leverRoom);
		leverRoom.setSouth(startingArea);
		
		leverRoom.setNorth(genericRoom1);
		genericRoom1.setSouth(leverRoom);
		
		genericRoom1.setWest(lockerRoom);
		lockerRoom.setEast(genericRoom1);
		
		genericRoom1.setNorth(darkRoom);
		darkRoom.setSouth(genericRoom1);
		
		darkRoom.setNorth(genericRoom2);
		genericRoom2.setSouth(darkRoom);
		
		genericRoom2.setNorth(irradiatedRoom);
		irradiatedRoom.setSouth(genericRoom2);
		
		lockerRoom.setNorth(toilet);
		toilet.setSouth(lockerRoom);
		
		toilet.setEast(darkRoom);
		darkRoom.setWest(toilet);
		
		genericRoom2.setWest(exit);
		exit.setEast(genericRoom2);
		//There are 9 two way connections meaning 18 in total set methods.
		}
	
	/**
	 * Parses the Game and returns the game state
	 * @return
	 */
	public GameState getGameState(){
		if(gameState == null){
			gameState = GsParser.generateFromGame(this);
		}

		return gameState;
	}

	/**
	 * Updates our gameState to reflect the current state of the Game
	 */
	public synchronized void clockTick() {
		gameState = GsParser.generateFromGame(this);
	}
	
	public void setMessage(String m){
		System.out.println("In game setting message.");
		message = m;
	}
	
	public String getMessage(){
		String temp = message;
		message = null;
		return temp;
	}

}
