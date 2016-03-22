package datastorage.savemanager.GameStateWriter;

import datastorage.GameState;
import exceptions.GameLogicException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nodes.BasicNode;
import nodes.ItemNode;
import nodes.PlayerNode;
import nodes.RoomNode;
import nodes.RoomNode.Wall;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class GameStateWriterImpl implements GameStateWriter{

	/**
	 * Attempts to write a save to a given path from a given GameState
	 *  
	 * @param gameState
	 * @param path
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws GameLogicException 
	 */
	@Override
	public void writeGameStateToPath(GameState gameState, String path) throws ParserConfigurationException, TransformerException, GameLogicException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		//Create root
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("ExcellentAdventure");
		doc.appendChild(rootElement);

		//Get gameRunning Element and add it
		Element gameRunningElement = this.getGameRunningElement(doc, gameState);
		rootElement.appendChild(gameRunningElement);

		//Get GameMode Element and add it
		Element gameModeElement = this.getGameModeElement(doc, gameState);
		rootElement.appendChild(gameModeElement);



		//Get RoomNodes Element and add it
		Element roomNodesElement = this.getRoomNodesElement(doc, gameState);
		rootElement.appendChild(roomNodesElement);

		//Get PlayerNodes Element and add it
		Element playerNodesElement = this.getPlayerNodesElement(doc, gameState);
		rootElement.appendChild(playerNodesElement);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		StreamResult result = new StreamResult(new File(path + File.separator +"ExcellentAdventure-Save.xml"));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	}

	/**
	 * Given a GameState return a gameRunningElement
	 * 
	 * @param doc
	 * @param gameState
	 * @return
	 */
	private Element getGameRunningElement(Document doc, GameState gameState) {
		
		String running = gameState.isGameRunning() ? "RUN" : "END";
		
		//GameMode child
		Element gameRunningElement = doc.createElement("GameRunning");
		gameRunningElement.appendChild(doc.createTextNode(running));
		return gameRunningElement;
	}

	/**
	 * Given a GameState return a gameModeElement
	 * 
	 * @param doc
	 * @param gameState
	 * @return
	 */
	private Element getGameModeElement(Document doc, GameState gameState) {

		String mode = gameState.getMode().name();

		//GameMode child
		Element gameModeElement = doc.createElement("GameMode");
		gameModeElement.appendChild(doc.createTextNode(mode));
		return gameModeElement;

	}

	/**
	 * Given a GameState return an element which contains all the Room Nodes
	 * 
	 * @param doc
	 * @param gameState
	 * @return
	 * @throws GameLogicException 
	 */
	private Element getRoomNodesElement(Document doc, GameState gameState) throws GameLogicException {

		//Create the root rooms element
		Element roomsElement = doc.createElement("Rooms");

		HashMap<Integer, RoomNode> roomMap = gameState.getRooms();

		//For each room parse it into an element and add it to the root rooms element
		for(Map.Entry<Integer, RoomNode> entry : roomMap.entrySet()){

			//Add id as the attribute
			Element roomElement = doc.createElement("Room");
			Attr attr = doc.createAttribute("id");
			attr.setValue(Integer.toString(entry.getKey()));
			roomElement.setAttributeNode(attr);

			RoomNode rNode = entry.getValue();

			ArrayList<BasicNode> children = rNode.getChildren();

			//Add all the items
			for(BasicNode bNode:children){

				Element childElement = this.getChildNodeElement(doc, bNode);
				if(childElement != null){
					roomElement.appendChild(childElement);
				}

			}

			//Add all the Walls

			Element northWallElement = getWallElement(doc, "North", rNode.getWall("north"));
			Element eastWallElement = getWallElement(doc, "East", rNode.getWall("east"));
			Element southWallElement = getWallElement(doc, "South", rNode.getWall("south"));
			Element westWallElement = getWallElement(doc, "West", rNode.getWall("west"));

			roomElement.appendChild(northWallElement);
			roomElement.appendChild(eastWallElement);
			roomElement.appendChild(southWallElement);
			roomElement.appendChild(westWallElement);

			//N

			//Append room to rooms
			roomsElement.appendChild(roomElement);
		}

		return roomsElement;
	}

	/**
	 * Given a node return an item element
	 * 
	 * If you attempt to parse a non-item element with this method an exception will be raised
	 * 
	 * @param doc
	 * @param bNode
	 * @return
	 * @throws GameLogicException 
	 */
	private Element getChildNodeElement(Document doc, BasicNode bNode) throws GameLogicException {

		if(bNode instanceof ItemNode){
			Element itemElement = doc.createElement("Item");
			Attr attr = doc.createAttribute("id");
			attr.setValue(Integer.toString(bNode.getId()));
			itemElement.setAttributeNode(attr);

			ItemNode iNode = (ItemNode)bNode;

			Element stateElement = doc.createElement("State");
			stateElement.appendChild(doc.createTextNode(iNode.getState().name()));
			itemElement.appendChild(stateElement);

			Element typeElement = doc.createElement("Type");
			typeElement.appendChild(doc.createTextNode(iNode.getType().name()));
			itemElement.appendChild(typeElement);

			return itemElement;
		}

		throw new GameLogicException("Attempted to parse a child which wasn't an item");
	}

	/**
	 * Given a wall and it's orientation return an element representation
	 * 
	 * @param doc
	 * @param orientation
	 * @param wall
	 * @return
	 */
	private Element getWallElement(Document doc, String orientation, Wall wall){

		Element wallElement = doc.createElement("Wall");
		Attr wallAttr = doc.createAttribute("Orientation");
		wallAttr.setValue(orientation);
		wallElement.setAttributeNode(wallAttr);

		wallElement.appendChild(doc.createTextNode(wall.name()));

		return wallElement;

	}

	/**
	 * Given a GameState return a Players node that contains all the Player Nodes 
	 * 
	 * @param doc
	 * @param gameState
	 * @return
	 * @throws GameLogicException
	 */
	private Element getPlayerNodesElement(Document doc, GameState gameState) throws GameLogicException {

		//Create root player element
		Element playersElement = doc.createElement("Players");

		ArrayList<PlayerNode> players = gameState.getPlayers();

		//iterate around all player, parsing their ID, Orientation, CurrentRoomId and Items into an element representation
		for(PlayerNode player: players){

			Element playerElement = doc.createElement("Player");
			Attr idAttr = doc.createAttribute("id");
			idAttr.setValue(Integer.toString(player.getID()));
			playerElement.setAttributeNode(idAttr);

			Element orientationElement = doc.createElement("Orientation");
			orientationElement.appendChild(doc.createTextNode(player.getOrientation().name()));
			playerElement.appendChild(orientationElement);

			Element currentRoomElement = doc.createElement("CurrentRoom");
			currentRoomElement.appendChild(doc.createTextNode(Integer.toString(player.getRoomID())));
			playerElement.appendChild(currentRoomElement);

			ArrayList<BasicNode> children = player.getChildren();

			for(BasicNode bNode:children){

				Element childElement = this.getChildNodeElement(doc, bNode);
				if(childElement != null){
					playerElement.appendChild(childElement);
				}

			}

			playersElement.appendChild(playerElement);

		}

		return playersElement;
	}

}
