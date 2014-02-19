package edu.umn.pubsub.common.exception;

/**
 * Thrown when the max article length is exceeded.
 * @author prashant
 *
 */
public class MaxArticleLengthExceededException extends Exception{

	private static final long serialVersionUID = 6981059015521417878L;
	
	public MaxArticleLengthExceededException() {
		super();
	}
	
	public MaxArticleLengthExceededException(String s) {
		super(s);
	}

}
