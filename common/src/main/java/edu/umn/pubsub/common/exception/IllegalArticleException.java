package edu.umn.pubsub.common.exception;

/**
 * Thrown when article string structure is not parsable
 * @author prashant
 *
 */
public class IllegalArticleException extends Exception {

	private static final long serialVersionUID = 9091061951158354783L;
	
	public IllegalArticleException(){
        super();
    }

    public IllegalArticleException(String s){
        super(s);
    }
}
