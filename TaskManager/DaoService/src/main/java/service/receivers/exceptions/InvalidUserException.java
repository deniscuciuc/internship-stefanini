package service.receivers.exceptions;

/**
 * Exception class for user errors
 * @author dcuciuc
 */
public class InvalidUserException extends Exception {
	public InvalidUserException(String errorMessage) {
		super(errorMessage);
	}
}
