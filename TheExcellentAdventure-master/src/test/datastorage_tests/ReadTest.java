package test.datastorage_tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import datastorage.Game;
import datastorage.savemanager.GameStateReader.GameStateReader;
import datastorage.savemanager.GameStateReader.GameStateReaderImpl;
import exceptions.GameSaveReadException;
import exceptions.InvalidSaveFileException;

public class ReadTest {

	private File saveFile;

	/**
	 * Sets up the test by loading preparing the save file by loading
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		URL saveUrl = ReadTest.class.getResource("ExcellentAdventure-Save.xml");
		
		String saveString = saveUrl.getFile();
		
		if(saveString.equals("")){
			throw new Exception("Could not find the save file to load, please ensure it is placed as a sibling of this test");
		}
		
		saveFile = new File(saveString);
	}

	/**
	 * Here we test the file for:
	 *  - if it's readable
	 *  - if it's a save file
	 *  - if it's a valid save file
	 *  
	 *  We only pass this test if we satisfy the above.
	 */
	@Test
	public void test() {
		
		GameStateReader gameStateReader = new GameStateReaderImpl();
		
		Game gs = null;
		
		try {
			gs = gameStateReader.readGameFromSave(saveFile.getAbsolutePath());
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
		
		assertNotNull(gs);
		
	}

}
