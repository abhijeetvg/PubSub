package edu.umn.pubsub.common.exception;

/**
 * Thrown when Client is not joined.
 * @author prashant
 *
 */
public class IllegalClientException extends Exception {

	private static final long serialVersionUID = -3639106654449422387L;

	public IllegalClientException() {
		super();
	}
	
	public IllegalClientException(String s) {
		super(s);
	}
}
