package edu.umn.pubsub.common.validator;

import java.util.regex.Pattern;

import edu.umn.pubsub.common.constants.ArticleConstants;
import edu.umn.pubsub.common.constants.Type;
import edu.umn.pubsub.common.util.StringUtil;

public final class ContentValidator {

	private ContentValidator() {
		// cannot instantiate, only static methods
	}
	
	/**
	 * Check if the type is a valid type.
	 * A valid type is one of
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
	 */
	public static boolean isValidType(String typeString) {
		return !(Type.getType(typeString) == null);
	}
	
	/**
	 * Checks if the IP is a valid IP.
	 * 
	 * @param ipString
	 * @return
	 */
	public static boolean isValidIp(String ipString) {
		if(ipString == null) {
			return false;
		}
		String ipPattern = 
		        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		return Pattern.compile(ipPattern).matcher(ipString).matches();    
	}
	
	/**
	 * Checks if the article string is a valid article
	 * <br>
	 * A valid article is one that satisfies following:
	 * <br> 
	 * <ul>
	 * 	<li>Has a contents field.
	 *  <li>Can be broken in exactly 4 strings by ";"
	 *  <li>If topic is present, its a valid topic
	 * </ul>
	 * 
	 * @param article String
	 * @return
	 */
	public static boolean isValidArticle(String articleString) {
		if(articleString == null) {
			return false;
		}
		String[] split = articleString.split(ArticleConstants.ARTICLE_DELIMITER,-1);
		if(split == null) {
			return false;
		}
		if(split.length != 4) {
			return false;
		}
		if(split[3] == null || split[3].isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the subscription string is a valid Subscription
	 * <br>
	 * A valid subscription is one that satisfies following:
	 * <br> 
	 * <ul>
	 *  <li> Can be broken in exactly 4 strings by ";"
	 * 	<li> Has a empty contents field (last string).
	 *  <li> At least one is non-empty: topic, originator, org
	 *  <li>If topic is present, its a valid topic 
	 * </ul>
	 * 
	 * @param article String
	 * @return
	 */
	public static boolean isValidSubscription(String subscriptionString) {
		if(subscriptionString == null) {
			return false;
		}
		String[] split = subscriptionString.split(ArticleConstants.ARTICLE_DELIMITER,-1);
		if(split == null) {
			return false;
		}
		if(split.length != 4) {
			return false;
		}
		// all org, topic and originator are null
		if(StringUtil.isEmpty(split[0]) && StringUtil.isEmpty(split[1]) && StringUtil.isEmpty(split[2])) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the port is a valid positive number.
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isValidPort(String string) {
		try{
			int parseInt = Integer.parseInt(string);
			if(parseInt < 0) {
				return false;
			}
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
