package datastorage.savemanager.GameStateWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import datastorage.GameState;
import exceptions.GameLogicException;

public interface GameStateWriter {

	/**
	 * Attempts to write a save to a given path from a given GameState
	 *  
	 * @param gameState
	 * @param path
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws GameLogicException 
	 */
	public void writeGameStateToPath(GameState gameState, String path) throws ParserConfigurationException, TransformerException, GameLogicException;
	
}
