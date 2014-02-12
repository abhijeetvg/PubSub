package edu.umn.pubsub.common.client;

import java.lang.reflect.Constructor;

import edu.umn.pubsub.common.validator.ContentValidator;

/**
 * Class for storing the attributes of client
 * @author prashant
 *
 */
public final class ClientInfo {
	private String ip;
	private int port;
	
	/**
	 * This {@link Constructor} throws {@link IllegalArgumentException} if the ip is not valid.
	 * @throws IllegalArgumentException
	 */
	public ClientInfo(String ip, int port) throws IllegalArgumentException{
		if(!ContentValidator.isValidIp(ip)) {
			// TODO prashant remove hardcoding
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
		ClientInfo other = (ClientInfo) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return ip  + ":" + port;
	}
}
