package exceptions;

public class GameLogicException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * GameLogicException
	 * @param message
	 */
	public GameLogicException(String message) {
        super(message);
    }
	
}
