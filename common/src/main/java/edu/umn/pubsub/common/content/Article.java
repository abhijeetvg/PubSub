package edu.umn.pubsub.common.content;

import edu.umn.pubsub.common.constants.ArticleConstants;
import edu.umn.pubsub.common.constants.Type;
import edu.umn.pubsub.common.exception.IllegalArticleException;
import edu.umn.pubsub.common.exception.IllegalArticleTypeException;
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
	
	public Article(String articleStr) throws IllegalArticleException, IllegalArticleTypeException{
		if(!ContentValidator.isValidArticle(articleStr)) {
			throw new IllegalArticleException("Invalid article structure: "+ articleStr);
		}
		String[] split = articleStr.split(ArticleConstants.ARTICLE_DELIMITER,-1);
		if(!ContentValidator.isValidType(split[0])) {
			throw new IllegalArticleTypeException("Illegal Article Type: " + split[0]);
		}
		this.type = Type.getType(split[0]);
		this.originator = split[1];
		this.org = split[2]; 
		this.contents = split[3];
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
