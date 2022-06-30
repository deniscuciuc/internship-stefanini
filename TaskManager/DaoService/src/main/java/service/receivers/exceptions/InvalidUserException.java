package service.receivers.exceptions;

@SuppressWarnings("serial")
public class InvalidUserException extends Exception {
	public InvalidUserException(String errorMessage) {
		super(errorMessage);
	}
}
