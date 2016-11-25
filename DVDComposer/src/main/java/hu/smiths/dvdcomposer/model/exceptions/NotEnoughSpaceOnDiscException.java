package hu.smiths.dvdcomposer.model.exceptions;

public class NotEnoughSpaceOnDiscException extends Exception {

	private static final long serialVersionUID = 4911616870390291275L;

	public NotEnoughSpaceOnDiscException(String message) {
		super(message);
	}

}
