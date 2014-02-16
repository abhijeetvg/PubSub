package edu.umn.pubsub.common.exception;

/**
 * Thrown when subscription string structure is not parsable
 * @author prashant
 *
 */
public class IllegalSubscriptionException extends Exception {
	
	private static final long serialVersionUID = -6062515405111135148L;

	public IllegalSubscriptionException(){
        super();
    }

    public IllegalSubscriptionException(String s){
        super(s);
    }
}
