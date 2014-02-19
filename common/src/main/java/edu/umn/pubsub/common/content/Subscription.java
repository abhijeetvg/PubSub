package edu.umn.pubsub.common.content;

import edu.umn.pubsub.common.constants.ArticleConstants;
import edu.umn.pubsub.common.constants.Type;
import edu.umn.pubsub.common.exception.IllegalArticleTypeException;
import edu.umn.pubsub.common.exception.IllegalSubscriptionException;
import edu.umn.pubsub.common.exception.MaxArticleLengthExceededException;
import edu.umn.pubsub.common.util.StringUtil;
import edu.umn.pubsub.common.validator.ContentValidator;

/**
 * Stores attributes of Subscription.
 * 
 * @author prashant
 *
 */
public final class Subscription {
	private Type type;
	private String originator;
	private String org;
	
	public Subscription(Type type, String originator, String org) {
		this.type = type;
		this.originator = originator;
		this.org = org;
	}
	
	public Subscription(String subscriptionStr) throws IllegalSubscriptionException, IllegalArticleTypeException, MaxArticleLengthExceededException{
		if(!ContentValidator.isValidSubscription(subscriptionStr)) {
			throw new IllegalSubscriptionException("Invalid subscription: " + subscriptionStr);
		}
		int length = subscriptionStr.length();
		if(length > ArticleConstants.MAX_ARTICLE_LENGTH) {
			throw new MaxArticleLengthExceededException("Subscription length: "+ length + "is greater than max: " + ArticleConstants.MAX_ARTICLE_LENGTH);
		}
		String[] split = subscriptionStr.split(ArticleConstants.ARTICLE_DELIMITER,-1);
		if(!ContentValidator.isValidType(split[0])) {
			throw new IllegalArticleTypeException("Illegal Article Type: " + split[0]);
		}
		this.type = Type.getType(split[0]);
		this.originator = split[1];
		this.org = split[2];
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
	
	/**
	 * Returns true if the current article matches the subscription.
	 * The NOT NULL type, originator and org from the subscription are matched.
	 * If any one of the above matches <code>true</code> is returned.
	 * 
	 * @param article
	 * @return
	 */
	public boolean matchesArticle(Article article) {
		// check type
		if(article.getType() != null && type != null && !article.getType().equals(type)) {
			return false;
		}
		// check originator
		if(!StringUtil.isEmpty(article.getOriginator()) && !StringUtil.isEmpty(originator) && !article.getOriginator().equals(originator)) {
			return false;
		}
		// check org
		if(!StringUtil.isEmpty(article.getOrg()) && !StringUtil.isEmpty(org) && !article.getOrg().equals(org)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result
				+ ((originator == null) ? 0 : originator.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscription other = (Subscription) obj;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
			return false;
		if (originator == null) {
			if (other.originator != null)
				return false;
		} else if (!originator.equals(other.originator))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return type + ArticleConstants.ARTICLE_DELIMITER + originator + ArticleConstants.ARTICLE_DELIMITER + org;
	}
}
