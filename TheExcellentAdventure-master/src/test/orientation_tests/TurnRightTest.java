package test.orientation_tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import controllers.SinglePlayer;
import datastorage.Game;
import datastorage.savemanager.GameStateReader.GameStateReader;
import datastorage.savemanager.GameStateReader.GameStateReaderImpl;
import exceptions.GameLogicException;
import exceptions.GameSaveReadException;
import exceptions.InvalidSaveFileException;
import gameworld.Orientation;
import gameworld.Player;

public class TurnRightTest {

	private Game game;

	@Before
	public void setUp() throws Exception {
		URL saveUrl = TurnRightTest.class.getResource("TestOrientationExcellentAdventure-Save.xml");
		
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
	 * Testing that turning right
	 */
	@Test
	public void test() {
		
		SinglePlayer singlePlayer = new SinglePlayer(game, 0);
		
		try {
			singlePlayer.turnRight();
		} catch (GameLogicException e) {
			fail(e.getLocalizedMessage());
		}

		Player pAfter = game.getPlayers().get(0);
		
		assertTrue("Player turned right", pAfter.getOrientation() == Orientation.EAST);

	}

}
