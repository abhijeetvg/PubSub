package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.udp.UDPConnInfo;
import edu.umn.pubsub.common.rmi.Communicate;

/**
 * Base class for all command types. One or more RMI methods are invoked by each command.
 *  
 * @author Abhijeet
 *
 */
public abstract class BaseCommand {
	
	private String[] arguments;
	private UDPConnInfo conInfo;
	
	public BaseCommand(String cmd, UDPConnInfo conInfo) {
		this.arguments = cmd.split(" ");
		this.conInfo = conInfo;
	}
	
	/**
	 * Execute the command using the RMI client.
	 * 
	 * @param client: RMI Client.
	 * @return boolean: true or false depending on the success or failure.
	 * @throws NumberFormatException
	 * @throws RemoteException
	 * @throws ClientNullException
	 */
	public abstract boolean execute(Communicate client) 
			throws NumberFormatException, RemoteException, ClientNullException;
	
	protected String getArgument(int position) {
		
		//sanity
		if (position <= 0 || position >= arguments.length) {
			throw new IllegalArgumentException();
		}
		
		return arguments[position];
	}
	
	public int getPort() {
		return conInfo.getPort();
	}
	
	public String getHost() {
		return conInfo.getHost();
	}
	
}
