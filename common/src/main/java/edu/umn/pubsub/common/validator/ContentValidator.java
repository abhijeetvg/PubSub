package edu.umn.pubsub.common.validator;

import edu.umn.pubsub.common.content.Type;

public final class ContentValidator {

	private ContentValidator() {
		// cannot instantiate, only static methods
	}
	
	public static boolean isValidType(String typeStr) {
		return !(Type.getType(typeStr) == null);
	}
	
	public static boolean isValidIp(String ip) {
		// TODO
		return true;
	}
	
	public static boolean isValidArticle(String articleStr) {
		// TODO
		return true;
	}
	
	public static boolean isValidSubscription(String subscriptionStr) {
		// TODO
		return true;
	}
}
