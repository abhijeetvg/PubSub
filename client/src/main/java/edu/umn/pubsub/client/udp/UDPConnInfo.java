package edu.umn.pubsub.client.udp;

/**
 * 
 * @author Abhijeet
 *
 */
public class UDPConnInfo {

	private String host;
	private int port;
	
	public UDPConnInfo(String hst, int prt) {
		this.host = hst;
		this.port = prt;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
