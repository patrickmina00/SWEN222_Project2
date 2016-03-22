package parser;

import gameworld.Orientation;
import gameworld.Player;
import gameworld.items.Item;
import gameworld.items.ItemState;
import gameworld.items.Type;
import gameworld.rooms.Room;

import java.util.*;

import datastorage.Game;
import datastorage.Game.GameMode;
import datastorage.GameState;
import nodes.*;
import nodes.RoomNode.Wall;

/**GsParser reads a String and returns a GameState via the static method generateGameState(String exp).
 * 
 * @author Daniel Tait
 *
 */

public class GsParser {
	
	
	/**
	 * Captures the Game's current state into the GameState and returns the result
	 * @param game
	 * @return
	 */
	public static GameState generateFromGame(Game game){
		
		boolean gameRun = game.isGameRunning();
		GameMode gm = game.getMode();
		HashMap<Integer,Room> rooms = game.getRooms();
		ArrayList<Player> players = game.getPlayers();
		String message = game.getMessage();
		
		HashMap<Integer,RoomNode> rMap = new HashMap<Integer,RoomNode>();
		ArrayList<PlayerNode> pNodes = new ArrayList<PlayerNode>();
		MessageNode mNode = new MessageNode(message);
		
		for(Room r: rooms.values()){
			RoomNode rNode = new RoomNode(r.getId(), parseToWall(r.getNorth()), parseToWall(r.getEast()), parseToWall(r.getSouth()), parseToWall(r.getWest()));
			
			for(Item i: r.getItems()){
				rNode.addChild(new ItemNode(i.getID(), i.getState(), i.getType()));
			}
			
			rMap.put(rNode.getID(), rNode);
		}
		
		for(Player p: players){
			
			PlayerNode pNode = new PlayerNode(p.getID(), p.getRoomId(), p.getOrientation());
			
			for(Item i: p.getItems()){
				pNode.addChild(new ItemNode(i.getID(), i.getState(), i.getType()));
			}
			
			pNodes.add(pNode);
			
		}
		
		GameState gameState = new GameState(rMap, pNodes, gm, mNode, gameRun);
		
		return gameState;
	}
	
	public static RoomNode.Wall parseToWall(Room r){
		if(r != null){
			return RoomNode.Wall.LOCKED;
		} else {
			return RoomNode.Wall.CLEAR;
		}
	}
	
	/**Takes a String expression that represents the current state of the room the player is
	 * present in and returns a RoomNode to represent that current state.
	 * 
	 * @param exp - Expression to be translated
	 * @return - RoomNode that represents current state of Room player is present in.
	 */
	public static GameState generateGameState(String exp){
		HashMap<Integer,RoomNode> rMap = new HashMap<Integer,RoomNode>();
		ArrayList<PlayerNode> pNodes = new ArrayList<PlayerNode>();
		MessageNode mn = null;
		
		boolean rNode = false;
		int lastRoomId = 0;
		
		StringTokenizer tok = new StringTokenizer(exp);
		boolean gameRunning = determineGameRunning(tok.nextToken());
		GameMode gm = determineGameMode(tok.nextToken());
		
		while(tok.hasMoreElements()){
			String temp = tok.nextToken();
			if(finalToken(temp)){break;}
			else if(determineItemNode(temp)){
				ItemNode in = new ItemNode(findID(temp),findItemState(temp),findItemType(temp));
				if(rNode){rMap.get(lastRoomId).addChild(in);
				}
				else{pNodes.get(pNodes.size() -1).addChild(in);}
			}
			else if(determineRoomNode(temp)){
				String n = tok.nextToken();
				String e = tok.nextToken();
				String s = tok.nextToken();
				String w = tok.nextToken();
				rMap.put(findID(temp),new RoomNode(findID(temp),determineWall(n),determineWall(e),determineWall(s),determineWall(w)));
				lastRoomId = findID(temp);
				rNode = true;
			}
			else if(determinePlayerNode(temp)){
				int rid = Integer.parseInt(tok.nextToken());
				String o = tok.nextToken();
				pNodes.add(new PlayerNode(findID(temp),rid,determineOrientation(o)));
				rNode = false;
			}
			else if (determineMessageNode(temp)){
				mn = new MessageNode(tok.nextToken());
			}
		}
		return new GameState(rMap, pNodes, gm, mn, gameRunning);
	}

	private static Type findItemType(String token) {
		char[] cArray = token.toCharArray();
		char temp = cArray[3];
		if (temp == 'A'){return Type.ANCHORED;}
		else {return Type.REMOVABLE;}
	}

	private static ItemState findItemState(String token) {
		char[] cArray = token.toCharArray();
		char temp = cArray[2];
		if (temp == 'T'){return ItemState.TRUE;}
		else if (temp == 'F'){return ItemState.FALSE;}
		else {return ItemState.DEFAULT;}
	}

	public static String generateExp(GameState gs){
		String exp = "";
		exp = exp.concat(generateGameRunningString(gs));
		exp = exp.concat(generateModeString(gs));
		exp = exp.concat(generateRoomStrings(gs));
		exp = exp.concat(generatePlayerStrings(gs));
		exp = exp.concat(generateMessageString(gs));
		return exp;
	}
	

	private static String generateGameRunningString(GameState gs) {
		String gr;
		if(gs.isGameRunning()){gr = "RUN ";}
		else {gr= "END ";}
		return gr;
	}

	private static String generateMessageString(GameState gs) {
		return "M "+gs.getMessageNode().toString()+" X";
	}



	private static String generatePlayerStrings(GameState gs) {
		String exp = "";
		for (PlayerNode pn : gs.getPlayers()){
			exp = exp.concat("P"+pn.getID()+" "+pn.getRoomID()+" ");
			exp = exp.concat(pn.getOrientationString());
			exp = exp.concat(generateItemStrings(pn.getChildren()));
		}
		return exp;
	}

	

	private static String generateRoomStrings(GameState gs) {
		String exp = "";
		for (int k : gs.getRooms().keySet()){
			RoomNode room = gs.getRooms().get(k);
			exp = exp.concat("R");
			String id = gs.getRooms().get(k).getID()+" ";
			String n = generateWallString(room.getWall("north"));
			String e = generateWallString(room.getWall("east"));
			String s = generateWallString(room.getWall("south"));
			String w = generateWallString(room.getWall("west"));
			exp = exp.concat(id).concat(n).concat(e).concat(s).concat(w);
			exp = exp.concat(generateItemStrings(room.getChildren()));
		}
		return exp;
	}

	private static String generateItemStrings(ArrayList<BasicNode> items) {
		String exp = "";
		for (BasicNode in : items){
			exp = exp.concat("I");
			exp = exp.concat(Integer.toString(in.getId()).concat(in.getStateString()).concat(in.getTypeString()));
		}
		return exp;
	}



	private static String generateWallString(Wall wall) {
		if(wall == Wall.CLEAR){return "C ";}
		else if (wall == Wall.LOCKED){return "L ";}
		else {return "O ";}
	}



	private static String generateModeString(GameState gs) {
		if(gs.getMode() == GameMode.SINGLE){return "S ";}
		else {return "M ";}  
	}



	private static GameMode determineGameMode(String tok) {
		if (tok.equals("S")){return GameMode.SINGLE;}
		else {return GameMode.MULTIPLAYER;}
	}

	private static boolean determineGameRunning(String tok) {
		if(tok.equals("RUN")){return true;}
		else if(tok.equals("END")){return false;}
		else{
			System.err.println("Unrecognised game running string. " + tok);
			return false;
		}
	}

	/**Determines the Orientation represented by String tok.
	 * 
	 * @param tok - Token to examine
	 * @return enum type Orientation.
	 */
	
	private static Orientation determineOrientation(String tok) {
		if(tok.equals("N")){return Orientation.NORTH;}
		else if (tok.equals("E")){return Orientation.EAST;}
		else if (tok.equals("S")){return Orientation.SOUTH;}
		else {return Orientation.WEST;}
	}
	
	/**Takes the unique ID number from the given String token and returns it.
	 * 
	 * @param token - Entire token
	 * @return Id given in the token
	 */
	
	private static int findID(String token) {
		return Integer.parseInt(token.replaceAll("[\\D]",""));
	}

	/**Determines if the given String token represents a RoomNode.
	 * 
	 * @param token - String to be examined
	 * @return True if token represents RoomNode, false otherwise.
	 */
	
	private static boolean determineRoomNode(String token){
		char[] temp = token.toCharArray();
		return temp[0] == 'R';
	}
	
	/**Determines if the given String token represents a PlayerNode.
	 * 
	 * @param token - String to be examined
	 * @return True if token represents PlayerNode, false otherwise.
	 */
	
	private static boolean determinePlayerNode(String token) {
		char[] temp = token.toCharArray();
		return temp[0] == 'P';
	}

	/**Determines if the given String token represents an ItemNode.
	 * 
	 * @param token - String to be examined
	 * @return True if token represents ItemNode, false otherwise.
	 */
	
	private static boolean determineItemNode(String token) {
		char[] temp = token.toCharArray();
		return temp[0] == 'I';
	}

	
	/**Determines the Doortype of a paticular wall given by the String dt.
	 * 
	 * @param dt - String representing state of door
	 * @return DoorType expressed by the token dt
	 */
	
	private static Wall determineWall(String token) {
		if (token.equals("L")){return Wall.LOCKED;}
		else if(token.equals("O")){return Wall.OPEN;}
		else {return Wall.CLEAR;}
	}
	
	/**Determines if the given String token represents the end of the expression.
	 * 
	 * @param token - String to be examined
	 * @return True if token represents end of expression, false otherwise.
	 */
	
	private static boolean finalToken(String tok) {
		return tok.equals("X");
	}
	
	/**Determines if the given String token represents a MessageNode.
	 * 
	 * @param token - String to be examined
	 * @return True if token represents MessageNode, false otherwise.
	 */
	
	private static boolean determineMessageNode(String temp) {
		return temp.equals("M");
	}
}
