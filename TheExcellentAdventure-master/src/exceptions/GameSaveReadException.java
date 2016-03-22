package exceptions;

public class GameSaveReadException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * GameSaveReadException when an exception occurs in the read process
	 * @param message
	 */
	public GameSaveReadException(String message) {
        super(message);
    }
	
}
