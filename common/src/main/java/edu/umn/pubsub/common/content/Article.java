package edu.umn.pubsub.common.content;

import edu.umn.pubsub.common.constants.ArticleConstants;
import edu.umn.pubsub.common.constants.Type;
import edu.umn.pubsub.common.validator.ContentValidator;

/**
 * Stores attributes of Article.
 * 
 * @author prashant
 *
 */
public final class Article {
	private Type type;
	private String originator;
	private String org;
	private String contents;
	
	/**
	 * This constructor throws {@link IllegalArgumentException} if contents are not having valid structure.
	 * 
	 * @param type
	 * @param originator
	 * @param org
	 * @param contents
	 * @throws IllegalArgumentException
	 */
	private Article(Type type, String originator, String org, String contents){
		this.type = type;
		this.originator = originator;
		this.org = org;
		this.contents = contents;
	}
	
	public Article(String articleStr) throws IllegalArgumentException{
		if(!ContentValidator.isValidArticle(articleStr)) {
			// TODO prashant remove hard coding
			throw new IllegalArgumentException("Invalid article structure");
		}
		String[] split = articleStr.split(ArticleConstants.ARTICLE_DELIMITER);
		new Article(Type.getType(split[0]), split[1], split[2], split[3]);
	}
	
	public Type getType() {
		return type;
	}

	public String getOriginator() {
		return originator;
	}

	public String getOrg() {
		return org;
	}

	public String getContents() {
		return contents;
	}
	
	@Override 
	public String toString() {
		return type + ArticleConstants.ARTICLE_DELIMITER + 
				originator + ArticleConstants.ARTICLE_DELIMITER + 
				org + ArticleConstants.ARTICLE_DELIMITER + 
				contents;
	}

}
