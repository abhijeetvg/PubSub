package edu.umn.pubsub.common.udp;

/**
 * Processes UDP data.
 * 
 * @author Abhijeet
 *
 */
public interface UDPData {

	/**
	 * Used to process UDP data retrieved by {@link} UDPServer.
	 * 
	 * @param data
	 */
	void process(String data);
	
}
