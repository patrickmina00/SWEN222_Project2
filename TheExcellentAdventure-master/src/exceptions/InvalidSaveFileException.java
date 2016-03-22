package exceptions;

public class InvalidSaveFileException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * InvalidSaveFileException when an exception occurs in the save process
	 * @param message
	 */
	public InvalidSaveFileException(String message) {
        super(message);
    }
	
}
