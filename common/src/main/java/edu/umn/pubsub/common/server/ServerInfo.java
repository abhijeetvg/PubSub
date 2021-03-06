package edu.umn.pubsub.common.server;

import edu.umn.pubsub.common.exception.IllegalIPException;
import edu.umn.pubsub.common.validator.ContentValidator;

/**
 * Class for storing the attributes of server
 * @author prashant
 *
 */
public final class ServerInfo {
	private String ip;
	private int port;
	private String bindingName;
	
	/**
	 * This will constructor will throw {@link IllegalArgumentException} if the ip is not valid
	 * @param ip
	 * @param port
	 * @throws IllegalArgumentException
	 */
	public ServerInfo(String ip, int port) throws IllegalIPException{
		if(!ContentValidator.isValidIp(ip)) {
			throw new IllegalIPException("Invalid Ip" + ip);
		}
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * This will constructor will throw {@link IllegalArgumentException} if the ip is not valid
	 * @param ip
	 * @param port
	 * @throws IllegalArgumentException
	 */
	public ServerInfo(String ip, String bindingName, int port) throws IllegalIPException{
		if(!ContentValidator.isValidIp(ip)) {
			throw new IllegalIPException("Invalid Ip" + ip);
		}
		this.ip = ip;
		this.bindingName = bindingName;
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getBindingName() {
		return bindingName;
	}
	
	@Override
	public String toString() {
		return ip + ":" + bindingName + ":" + port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
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
		ServerInfo other = (ServerInfo) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	
}
