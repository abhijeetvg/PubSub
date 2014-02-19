package edu.umn.pubsub.client.udp;

import edu.umn.pubsub.client.Client;
import edu.umn.pubsub.common.udp.PrintLock;
import edu.umn.pubsub.common.udp.UDPData;

/**
 * 
 * Data transfer is done using UDP, RMI takes care of control layer.
 * 
 * Processes data received by RMI client from RMI Server via UDP.
 *  
 * @author Abhijeet
 *
 */
public class UDPClientData implements UDPData{

	@Override
	public void process(String data) {

		//TODO: Maybe write it into a file, so it does not have to be 
		//synchronized with client shell.
		synchronized (PrintLock.printLock) {
			
			if (!Client.isJoined) {
				return;
			}
			
			System.out.println("\n\n\t**Interrupted, got article from Server**");
			System.out.println("\t" + data);
		}
	}

}
