package hu.smiths.dvdcomposer.model.exceptions;

public class TooLargeFolderException extends RuntimeException {

	private static final long serialVersionUID = 7702656815091944811L;

	public TooLargeFolderException(String message) {
		super(message);
	}
}
