package test.parser_tests;

import static org.junit.Assert.assertTrue;
import gameworld.Orientation;
import gameworld.Player;
import gameworld.items.Item;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.rooms.Room;
import gameworld.rooms.RoomManager;
import gameworld.rooms.RoomManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;

import nodes.BasicNode;
import nodes.PlayerNode;
import nodes.RoomNode;

import org.junit.Before;
import org.junit.Test;

import parser.GsParser;
import datastorage.Game;
import datastorage.Game.GameMode;
import datastorage.GameState;

public class GenerateGameTest {

	private GameState gs;
	private Game game;

	@Before
	public void setUp() throws Exception {
		ItemManager itemManager = new ItemManagerImpl();
		RoomManager roomManager = new RoomManagerImpl();
		
		gs = GsParser.generateGameState("RUN S R0 L C C C I0DA R1 L C L C I1FA I2FA I3FA R2 L C L L R3 L L C C I5DA I6DR I13DA I4DR R4 L C L L R5 C L L C I8DA I7DA I5DA R6 L C L L R7 C C L C I9DR R8 C L C C I10TA I11FA P0 0 N I4DR I9DR M EMPTY X");
		
		GameMode gameMode = gs.getMode();
		
		ArrayList<PlayerNode> playerNodes = gs.getPlayers();
		HashMap<Integer, RoomNode> roomNodeMap = gs.getRooms();
		
		ArrayList<Player> players = new ArrayList<Player>();
		HashMap<Integer, Room> rooms = new HashMap<Integer, Room>();
		
		for(PlayerNode pNode: playerNodes){
			
			//setup player
			int uid = pNode.getID();
			
			int rid = pNode.getRoomID();
			
			Orientation orientation = pNode.getOrientation();
			
			Player p = new Player(uid, rid, orientation);
			
			//add items to player
			ArrayList<BasicNode> playerItems = pNode.getChildren();
			
			for(BasicNode bNode : playerItems){

				int itemId = bNode.getId();
				ItemState itemState = bNode.getState();
				
				Item item = itemManager.getItemWithIdAndState(itemId, itemState);
				
				p.addItem(item);
				
			}
			
			//add player
			players.add(p);
			
		}
		
		for(RoomNode rNode: roomNodeMap.values()){
			
			//setup room
			int rid = rNode.getID();
			
			//add items to player
			ArrayList<BasicNode> roomItems = rNode.getChildren();
			
			ArrayList<Item> items = new ArrayList<Item>();
			
			for(BasicNode bNode : roomItems){

				int itemId = bNode.getId();
				ItemState itemState = bNode.getState();
				
				Item item = itemManager.getItemWithIdAndState(itemId, itemState);
				
				items.add(item);
				
			}
			
			
			Room room = roomManager.getRoomWithIdAndItems(rid, items);

			//add room
			rooms.put(room.getId(), room);
	
		}
		
		game = new Game(gameMode, players, rooms);
	}

	/**
	 * Making sure the generated game is valid (not throwing any exceptions) and is not holding null or empty things
	 */
	@Test
	public void test() {

		ArrayList<Player> players = game.getPlayers();
		HashMap<Integer, Room> rooms = game.getRooms();
		GameMode gameMode = game.getMode();
		
		boolean notNullOrEmpty = true;
		
		if(gameMode == null){
			notNullOrEmpty = false;
		}
		
		if(players == null || players.isEmpty()){
			notNullOrEmpty = false;
		}
		
		if(rooms == null || rooms.isEmpty() ){
			notNullOrEmpty = false;
		}
		
		assertTrue("The generated game is not null or empty", notNullOrEmpty);
		
		
	}

}
