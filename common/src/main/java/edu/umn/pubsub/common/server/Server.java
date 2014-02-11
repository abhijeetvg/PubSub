package edu.umn.pubsub.common.server;

import edu.umn.pubsub.common.validator.ContentValidator;

/**
 * Class for storing the attributes of server
 * @author prashant
 *
 */
public final class Server {
	private String ip;
	private int port;
	
	/**
	 * This will constructor will {@link IllegalArgumentException} if the ip is not valid
	 * @param ip
	 * @param port
	 * @throws IllegalArgumentException
	 */
	public Server(String ip, int port) throws IllegalArgumentException{
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
