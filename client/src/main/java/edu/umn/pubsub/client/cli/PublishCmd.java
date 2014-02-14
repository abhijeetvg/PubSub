package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.common.rmi.Communicate;

/**
 * Invokes following RMI method on Server:
 * 
 *   Publish: Sends a new article to the server.
 *   PublishServer: Sends a new article to the server, sender is a server.
 * 
 * @author Abhijeet
 *
 */
public class PublishCmd extends BaseCommand {
	
	private final int ARG_HOST = 1;
	private final int ARG_PORT = 2;
	private final int ARG_ARTICLE = 3;
	
	private boolean isServerCall;

	public PublishCmd(String cmd) {
		this(cmd, false);
	}

	public PublishCmd(String cmd, boolean isServerCall) {
		super(cmd);
		this.isServerCall = isServerCall;
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

		if (isServerCall) {
			return client.PublishServer(getArgument(ARG_HOST), 
					Integer.parseInt(getArgument(ARG_PORT)), 
					getArgument(ARG_ARTICLE));
		}
		
		return client.Publish(getArgument(ARG_HOST), 
				Integer.parseInt(getArgument(ARG_PORT)), 
				getArgument(ARG_ARTICLE));
		
	}

}
