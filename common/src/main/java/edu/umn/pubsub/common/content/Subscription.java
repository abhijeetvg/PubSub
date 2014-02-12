package edu.umn.pubsub.common.content;

import edu.umn.pubsub.common.constants.ArticleConstants;
import edu.umn.pubsub.common.constants.Type;
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
	
	public Subscription(String subscriptionStr) {
		if(!ContentValidator.isValidSubscription(subscriptionStr)) {
			// TODO prashant remove hard coding
			throw new IllegalArgumentException("Invalid subscription : " + subscriptionStr);
		}
		String[] split = subscriptionStr.split(ArticleConstants.ARTICLE_DELIMITER);
		new Subscription(Type.getType(split[0]),split[1],split[2]);
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
	 * 
	 * @param article
	 * @return
	 */
	public boolean matchesArticle(Article article) {
		// check type
		if(type != null && !type.equals(article.getType())) {
			return false;
		}
		// check originator
		if(originator != null && !originator.equals(article.getOriginator())) {
			return false;
		}
		// check org
		if(org != null && !org.equals(article.getOrg())) {
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
