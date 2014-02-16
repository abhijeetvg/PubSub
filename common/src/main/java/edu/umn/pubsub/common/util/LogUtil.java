package edu.umn.pubsub.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Methods for logging purposes.
 * The methods will sys out the messages along with different attributes like timestamp.
 * 
 * @author prashant
 *
 */
public final class LogUtil {
	private final static SimpleDateFormat simpleDateFomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private LogUtil() {
		// Util class, cannot be instantiated
	}
	
	private static String currentTime() {
		return simpleDateFomat.format(new Date());
	}
	
	public static void log(String method, String message) {
		System.out.println(currentTime() + "\t" + method + "\t" + message);
	}
}
