package edu.umn.pubsub.common.content;

/**
 * All the topics supported by the system.
 * 
 * @author prashant
 *
 */
public enum Type {
	SPORTS("Sports"),
	LIFESTYLE("Lifestyle"),
	ENTERTAINMENT("Entertainment"),
	BUSINESS_TECHNOLOGY("Business Technology"),
	SCIENCE("Science"),
	POLITICS("Politics"),
	HEALTH("Health");
	
	private final String topic;
	
	private Type(String topic) {
		this.topic = topic; 
	}
	
	/**
	 * Returns Topic enum matching passed string
	 * 
	 * @param typeStr
	 * @return
	 */
	public static Type getType(String typeStr) {
		if(typeStr == null) {
			throw new NullPointerException();
		}
		for(Type value : Type.values()) {
			if(value.toString().equals(typeStr)) {
				return value;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return topic;
	}
}
