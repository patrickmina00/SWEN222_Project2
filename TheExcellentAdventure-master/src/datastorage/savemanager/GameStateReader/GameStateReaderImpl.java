package datastorage.savemanager.GameStateReader;

import gameworld.Orientation;
import gameworld.Player;
import gameworld.items.Item;
import gameworld.items.ItemManager;
import gameworld.items.ItemManagerImpl;
import gameworld.items.ItemState;
import gameworld.rooms.Room;
import gameworld.rooms.RoomManager;
import gameworld.rooms.RoomManagerImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import datastorage.Game;
import datastorage.Game.GameMode;
import exceptions.GameSaveReadException;
import exceptions.InvalidSaveFileException;

/**
 * GameStateReaderImpl implements GameStateReader.
 * 
 * It's singular function is return a Game given a path to a save file.
 * 
 * @author michaelwinton
 *
 */
public class GameStateReaderImpl implements GameStateReader {

	/**
	 * Attempts to read a path and return a Game
	 * 
	 * This is successful if:
	 *  - it's readable
	 *  - it's a save file
	 *  - it's a valid save file
	 * 
	 * @param loadPath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws InvalidSaveFileException
	 * @throws DOMException
	 * @throws GameSaveReadException
	 */
	@Override
	public Game readGameFromSave(String loadPath) throws ParserConfigurationException, SAXException, IOException, InvalidSaveFileException, DOMException, GameSaveReadException {

		//Prepares the doc for reading
		File loadFile = new File(loadPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(loadFile);

		//Normalizes so it's readable
		doc.getDocumentElement().normalize();

		//Check's that this a save file
		this.verifySave(doc);

		//Attempts to read game
		Game game = readGame(doc);

		return game;
	}

	/**
	 * Checks that this is a valid save by looking at the files root element.
	 * 
	 * 
	 * @param doc
	 * @throws InvalidSaveFileException
	 */
	private void verifySave(Document doc) throws InvalidSaveFileException {

		String rootNode = doc.getDocumentElement().getNodeName();

		//Expecting "ExcellentAdventure"
		if(!rootNode.equals("ExcellentAdventure")){
			throw new InvalidSaveFileException("Could not verify that we are loading a valid save file");
		}

	}

	/**
	 * Reads the save file by;
	 * - first, reading the game mode
	 * - second, reading the rooms
	 * - third, reading the players
	 * 
	 * returns a game on success
	 * 
	 * @param doc
	 * @return
	 * @throws DOMException
	 * @throws GameSaveReadException
	 */
	private Game readGame(Document doc) throws DOMException, GameSaveReadException {


		GameMode gameMode = readGameMode(doc);
		boolean gameRunning = readGameRunning(doc);
		HashMap<Integer, Room> rooms = readRooms(doc);
		ArrayList<Player> players = readPlayers(doc);

		Game game = new Game(gameMode, players, rooms);

		game.setGameRunning(gameRunning);
		
		return game;
	}

	private boolean readGameRunning(Document doc) {

		NodeList gameRunningNodeList = doc.getElementsByTagName("GameRunning");

		Node gameRunningeNode = gameRunningNodeList.item(0);

		String runningText = gameRunningeNode.getFirstChild().getNodeValue();
		
		boolean isRunning = runningText.equalsIgnoreCase("RUN");

		return isRunning;
	}

	/**
	 * Reads all the players in a save and returns them in an ArrayList
	 * 
	 * @param doc
	 * @return ArrayList<Player> players
	 * @throws DOMException
	 * @throws GameSaveReadException
	 */
	private ArrayList<Player> readPlayers(Document doc) throws DOMException, GameSaveReadException {

		ArrayList<Player> players = new ArrayList<Player>();

		NodeList playerNodes = doc.getElementsByTagName("Player");

		for (int index = 0; index < playerNodes.getLength(); index++) {

			Node playerNode = playerNodes.item(index);

			if (playerNode.getNodeType() == Node.ELEMENT_NODE) {

				Element playerElement = (Element) playerNode;

				Player player = this.readPlayer(doc, playerElement);

				players.add(player);

			}

		}

		return players;
	}

	/**
	 * Reads a Player element.
	 * 
	 * Parsing it's;
	 * - ID
	 * - Orientation
	 * - CurrentRoomId
	 * - Items
	 * 
	 * @param doc
	 * @param playerElement
	 * @return
	 * @throws DOMException
	 * @throws GameSaveReadException
	 */
	private Player readPlayer(Document doc, Element playerElement) throws DOMException, GameSaveReadException {

		ArrayList<Item> items = readItems(doc, playerElement);

		//Player Id
		int playerId = Integer.parseInt(playerElement.getAttribute("id"));

		//Orientation
		NodeList orientationList = playerElement.getElementsByTagName("Orientation");

		Node orientationNode = orientationList.item(0);

		Orientation orientation = this.getOrientation(orientationNode.getFirstChild().getNodeValue());

		//Current room id
		NodeList currentRoomList = playerElement.getElementsByTagName("CurrentRoom");

		Node currentRoomNode = currentRoomList.item(0);

		int curRoomId = Integer.parseInt( currentRoomNode.getFirstChild().getNodeValue() );

		//Creating the player
		Player player = new Player(playerId, curRoomId, orientation);
		for(Item i : items){
			player.addItem(i);
		}

		return player;
	}

	/**
	 * Reads the game mode
	 * 
	 * @param doc
	 * @return
	 * @throws DOMException
	 * @throws GameSaveReadException
	 */
	private GameMode readGameMode(Document doc) throws DOMException, GameSaveReadException {

		NodeList gameModeNodeList = doc.getElementsByTagName("GameMode");

		Node gameModeNode = gameModeNodeList.item(0);

		GameMode gameMode = this.getGameMode(gameModeNode.getFirstChild().getNodeValue());

		return gameMode;
	}


	/**
	 * Reads all the rooms in a save and returns them in an ArrayList
	 * 
	 * @param doc
	 * @return ArrayList<Player> players
	 * @throws DOMException
	 * @throws GameSaveReadException
	 */
	private HashMap<Integer, Room> readRooms(Document doc) throws GameSaveReadException {

		HashMap<Integer, Room> roomMap = new HashMap<Integer, Room>();

		RoomManager roomManager = new RoomManagerImpl();

		NodeList roomNodes = doc.getElementsByTagName("Room");

		for (int index = 0; index < roomNodes.getLength(); index++) {

			Node roomNode = roomNodes.item(index);

			if (roomNode.getNodeType() == Node.ELEMENT_NODE) {

				Element roomElement = (Element) roomNode;

				ArrayList<Item> items = readItems(doc, roomElement);

				int roomID = Integer.parseInt(roomElement.getAttribute("id"));

				Room room = roomManager.getRoomWithIdAndItems(roomID, items);

				roomMap.put(room.getId(), room);

			}

		}

		return roomMap;
	}

	/**
	 * Reads all the items in the given element and returns them in an ArrayList
	 * 
	 */
	private ArrayList<Item> readItems(Document doc, Element roomElement) throws GameSaveReadException {

		ArrayList<Item> items = new ArrayList<Item>();

		NodeList itemNodes = roomElement.getElementsByTagName("Item");

		for (int index = 0; index < itemNodes.getLength(); index++) {

			Node itemNode = itemNodes.item(index);

			if (itemNode.getNodeType() == Node.ELEMENT_NODE) {

				Element itemElement = (Element) itemNode;

				Item item = readItem(itemElement);

				items.add(item);

			}

		}

		return items;
	}

	/**
	 * Reads an item element.
	 * 
	 * Parsing it's;
	 * - ID
	 * - State
	 */
	private Item readItem(Element itemElement) throws GameSaveReadException {

		int id = Integer.parseInt( itemElement.getAttribute("id") );
		ItemState state = null;


		NodeList nodes = itemElement.getChildNodes();

		for (int index = 0; index < nodes.getLength(); index++) {

			Node node = nodes.item(index);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				if(node.getNodeName().equals("State")){

					String stateString = node.getFirstChild().getNodeValue();

					state = this.getState(stateString);

				}

			}

		}

		ItemManager itemManager = new ItemManagerImpl();

		return itemManager.getItemWithIdAndState(id, state);
	}

	//ENUM PARSING

	private ItemState getState(String stateString) throws GameSaveReadException{

		if(ItemState.DEFAULT.name().equals(stateString)){
			return ItemState.DEFAULT;
		} else if (ItemState.TRUE.name().equals(stateString)){
			return ItemState.TRUE;
		} else if (ItemState.FALSE.name().equals(stateString)){
			return ItemState.FALSE;
		} else {

			throw new GameSaveReadException("Could not get State");

		}
	}

	private GameMode getGameMode(String gameModeString) throws GameSaveReadException{

		if(GameMode.SINGLE.name().equals(gameModeString)){
			return GameMode.SINGLE;
		} else if (GameMode.MULTIPLAYER.name().equals(gameModeString)){
			return GameMode.MULTIPLAYER;
		} else {

			throw new GameSaveReadException("Could not get GameMode");

		}
	}

	private Orientation getOrientation(String orientationString) throws GameSaveReadException{

		if(Orientation.NORTH.name().equals(orientationString)){
			return Orientation.NORTH;
		} else if (Orientation.EAST.name().equals(orientationString)){
			return Orientation.EAST;
		} else if (Orientation.SOUTH.name().equals(orientationString)){
			return Orientation.SOUTH;
		} else if (Orientation.WEST.name().equals(orientationString)){
			return Orientation.WEST;
		} else {

			throw new GameSaveReadException("Could not get Orientation");

		}
	}


}
