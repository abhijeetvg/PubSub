package edu.umn.pubsub.common.content;

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
			throw new IllegalArgumentException("Invalid subscription : " + subscriptionStr);
		}
		String[] split = subscriptionStr.split(";");
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
}
