package edu.umn.pubsub.common.client;

import java.lang.reflect.Constructor;

import edu.umn.pubsub.common.validator.ContentValidator;

/**
 * Class for storing the attributes of client
 * @author prashant
 *
 */
public final class Client {
	private String ip;
	private int port;
	
	/**
	 * This {@link Constructor} throws {@link IllegalArgumentException} if the ip is not valid.
	 * 
	 * @param ip
	 * @param port
	 * @throws IllegalArgumentException
	 */
	public Client(String ip, int port) throws IllegalArgumentException{
		if(!ContentValidator.isValidIp(ip)) {
			throw new IllegalArgumentException("Invalid Ip" + ip);
		}
		this.ip = ip;
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
}
