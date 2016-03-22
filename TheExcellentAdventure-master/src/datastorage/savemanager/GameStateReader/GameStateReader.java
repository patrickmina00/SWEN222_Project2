package datastorage.savemanager.GameStateReader;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import datastorage.Game;
import exceptions.GameSaveReadException;
import exceptions.InvalidSaveFileException;

public interface GameStateReader {
	
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
	public Game readGameFromSave(String loadPath) throws ParserConfigurationException, SAXException, IOException, InvalidSaveFileException, DOMException, GameSaveReadException;

}
