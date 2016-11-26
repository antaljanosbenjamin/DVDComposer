package hu.smiths.dvdcomposer.model.exceptions;

public class InvalidResultException extends Exception {

	private static final long serialVersionUID = 7630960202545741640L;

	public InvalidResultException(Throwable cause) {
		super(cause);
	}
	
	public InvalidResultException(String message){
		super(message);
	}
}
