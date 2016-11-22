package hu.smiths.dvdcomposer.model.exceptions;

public class CannotLoadAlgorithmClass extends Exception {

	private static final long serialVersionUID = 6233246817277641986L;

	public CannotLoadAlgorithmClass(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotLoadAlgorithmClass(String message) {
		super(message);
	}
}
