package hu.smiths.dvdcomposer.model.exceptions;

public class CannotSaveModel extends Exception {

	private static final long serialVersionUID = -3769538114303863634L;

	public CannotSaveModel(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotSaveModel(String message) {
		super(message);
	}
}
