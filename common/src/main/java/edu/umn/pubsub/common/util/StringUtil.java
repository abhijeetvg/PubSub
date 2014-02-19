package edu.umn.pubsub.common.util;

import java.util.HashSet;
import java.util.Set;

import edu.umn.pubsub.common.exception.IllegalIPException;
import edu.umn.pubsub.common.server.ServerInfo;

/**
 * General util methods for string manipulation
 * @author prashant
 * @author Abhijeet
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
	
	public static String getCmdPrefix(String string) {
		return string.split(" ")[0];
	}
	
	public static Set<ServerInfo> parseGetListResult(String getListResult, String serverIp) {
		//String method = ".parseGetListResult()";
		final String commandDelimiter = ";";
		Set<ServerInfo> activeServers = new HashSet<ServerInfo>();
		if(getListResult == null) {
			LogUtil.info("Got null result in getList");
			return activeServers;
		}
		String[] split = getListResult.split(commandDelimiter);
		if (null == split) {
			return activeServers;
		}
		for(int i = 0, j = 2; j < split.length; j = i + 3) {
			if(split[i].equals(serverIp)) {
				// Do not add our own server
				continue;
			}
			try {
				activeServers.add(new ServerInfo(split[i], split[i+1], Integer.parseInt(split[i+2])));
			} catch (NumberFormatException e) {
				LogUtil.info("Got Invalid port: " + split[i+2] + " in getList");
			} catch (IllegalIPException e) {
				LogUtil.info("Got Invalid IP: " + split[i] + " in getList");
			}
			
			i = i + 3;
		}
		return activeServers;
	}

}
