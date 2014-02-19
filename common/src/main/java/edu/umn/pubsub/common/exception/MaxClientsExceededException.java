package edu.umn.pubsub.common.exception;

/**
 * Thrown when max clients are reached
 * @author prashant
 *
 */
public class MaxClientsExceededException extends Exception {
	private static final long serialVersionUID = 2583369093377494135L;
	
	public MaxClientsExceededException() {
		super();
	}
	
	public MaxClientsExceededException(String s) {
		super(s);
	}
}
