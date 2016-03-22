package datastorage;

import java.util.*;

import datastorage.Game.GameMode;
import nodes.*;

/** Represents the current state of the game
 * 
 * @author Daniel Tait
 *
 */

public class GameState {

	private HashMap<Integer,RoomNode> rooms;
	private ArrayList<PlayerNode> players;
	private MessageNode message;
	private GameMode mode;
	private boolean gameRunning;
	
	/**
	 * Creates a new instance of GameState
	 * 
	 * @param r
	 * @param p
	 * @param g
	 * @param m
	 * @param gRun TODO
	 */
	public GameState(HashMap<Integer,RoomNode> r, ArrayList<PlayerNode> p, GameMode g, MessageNode m, boolean gRun){
		rooms = r;
		players = p;
		mode = g;
		message = m;
		gameRunning = gRun;
	}

	public HashMap<Integer,RoomNode> getRooms() {
		return rooms;
	}

	public ArrayList<PlayerNode> getPlayers() {
		return players;
	}

	public GameMode getMode() {
		return mode;
	}
	
	public MessageNode getMessageNode(){
		return message;
	}
	
	public void setMessageNode(MessageNode m){
		message = m;
	}
	
	/**
	 * Given a uid attempt to return the corresponding player Node
	 * @param uid
	 * @return
	 */
	public PlayerNode player(int uid) {
		for(PlayerNode p: players){
			if(p.getID() == uid){
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Given a RIM attempt to return the corresponding roomNode
	 * @param rid
	 * @return
	 */
	public RoomNode room(int rid) {
		if(rooms.containsKey(rid)){
			return rooms.get(rid);
		}
		return null;
	}
	
	public boolean isGameRunning(){
		return gameRunning;
	}
	
	/**
	 * FOR DEBUGGING:
	 * 
	 * Print the game state
	 */
	public void printGameState(){

		System.out.println("MODE: "+getMode().toString());
	
		for (RoomNode rn : getRooms().values()){
			System.out.println(rn.toString());
			System.out.println("ITEMS:");
			for (BasicNode bn : rn.getChildren()){
				ItemNode in = (ItemNode) bn;
				System.out.println(in.toString());
			}
			System.out.println("");
		}
		
		for (PlayerNode pn : getPlayers()){
			System.out.println(pn.toString());
			System.out.println("ITEMS:");
			for (BasicNode bn : pn.getChildren()){
				ItemNode in = (ItemNode) bn;
				System.out.println(in.toString());
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("MESSAGE : "+message.toString());
	}
}
