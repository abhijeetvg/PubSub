package edu.umn.pubsub.common.util;

/**
 * General util methods for string manipulation
 * @author prashant
 *
 */
public final class StringUtil {
	private StringUtil() {
		// Util class, cannot be instantiated
	}
	
	/**
	 * Along with {@code isEmpty()} check from {@link String} class also checks if the string is {@literal null}
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}
}
