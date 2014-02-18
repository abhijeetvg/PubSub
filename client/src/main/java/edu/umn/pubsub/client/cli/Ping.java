package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.udp.UDPConnInfo;
import edu.umn.pubsub.common.rmi.Communicate;

/**
 * Invokes following RMI method on Server:
 * 
 *   Ping: Check whether the server is up.
 *   
 * @author Abhijeet
 *
 */
public class Ping extends BaseCommand {

	public Ping(String cmd, UDPConnInfo conInfo) {
		super(cmd, conInfo);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Communicate client) throws NumberFormatException,
			RemoteException, ClientNullException {
		
		if (null == client) {
			throw new ClientNullException(CommandConstants.ERR_RMI_CLIENT_NULL);
		}

		return client.Ping();
	}

}
