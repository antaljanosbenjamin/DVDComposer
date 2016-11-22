package hu.smiths.dvdcomposer.model.exceptions;

public class CannotFindValidAssignmentException extends Exception {

	private static final long serialVersionUID = -7464424346708119011L;

	public CannotFindValidAssignmentException(String message){
		super(message);
	}
	
	public CannotFindValidAssignmentException(String message, Throwable cause){
		super(message, cause);
	}
	
}
