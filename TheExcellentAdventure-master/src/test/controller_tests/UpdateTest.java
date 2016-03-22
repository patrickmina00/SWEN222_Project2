package test.controller_tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

import nodes.ItemNode;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import controllers.SinglePlayer;
import datastorage.Game;
import datastorage.savemanager.GameStateReader.GameStateReader;
import datastorage.savemanager.GameStateReader.GameStateReaderImpl;
import exceptions.GameSaveReadException;
import exceptions.InvalidSaveFileException;
import gameworld.items.Item;
import gameworld.items.ItemState;
import gameworld.items.Type;
import gameworld.rooms.Room;

public class UpdateTest {

	private Game game;

	@Before
	public void setUp() throws Exception {
		URL saveUrl = UpdateTest.class.getResource("TestControllerExcellentAdventure-Save.xml");

		String saveString = saveUrl.getFile();

		if(saveString.equals("")){
			throw new Exception("Could not find the save file to load, please ensure it is placed as a sibling of this test");
		}

		File saveFile = new File(saveString);

		GameStateReader gameStateReader = new GameStateReaderImpl();

		game = null;

		try {
			game = gameStateReader.readGameFromSave(saveFile.getAbsolutePath());
		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			fail("Could not succesfully read the file");
			e.printStackTrace();
		} catch (InvalidSaveFileException e) {
			fail("Could not identify the file as a save file");
			e.printStackTrace();
		}  catch (GameSaveReadException e) {
			fail("Could not succesfully parse save file");
			e.printStackTrace();
		}
	}


	/**
	 * Testing that update works
	 */
	@Test
	public void test() {

		SinglePlayer singlePlayer = new SinglePlayer(game, 0);

		ItemNode testItemNode = new ItemNode(2, ItemState.TRUE, Type.ANCHORED);

		singlePlayer.updateItem(testItemNode);

		Room room = game.getRooms().get(1);

		ArrayList<Item> items = room.getItems();

		for(Item item : items){

			int i = item.getID();

			//Checking that the item (with id = 9) is now in the room
			if(i == 2){

				assertTrue("Sucessfully updated item", item.getState() == ItemState.TRUE);


			}

		}


	}

}
