package edu.umn.pubsub.common.exception;

/**
 * Thrown when the type in the article is not valid
 *  A valid type is one of
	 * <ul> 
	 * 	<li>Sports
	 * 	<li>Lifestyle
	 * 	<li>Entertainment
	 * 	<li>Business Technology
	 * 	<li>Science
	 * 	<li>Politics
	 * 	<li>Health
	 * </ul>
 * 
 * @author prashant
 *
 */
public class IllegalArticleTypeException extends Exception {

	private static final long serialVersionUID = -7525403427276160598L;

	public IllegalArticleTypeException() {
		super();
	}
	
	public IllegalArticleTypeException(String s) {
		super(s);
	}
}
