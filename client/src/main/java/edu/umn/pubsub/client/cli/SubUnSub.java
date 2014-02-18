package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.udp.UDPConnInfo;
import edu.umn.pubsub.common.rmi.Communicate;

/**
 * Invokes following RMI method on Server:
 * 
 *   Subscribe: Request a subscription to group server.
 *   Unsubscribe: Request an unsubscribe to group server.
 * 
 * @author Abhijeet
 *
 */
public class SubUnSub extends BaseCommand {

	private final int ARG_ARTICLE = 1;
	
	private int cmdType;
	
	public SubUnSub(String cmd, UDPConnInfo conInfo, int doCommand) {
		super(cmd, conInfo);
		this.cmdType = doCommand;
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
		
		if (cmdType == CommandConstants.DO_COMMAND) {
			client.Subscribe(getHost(),getPort(), getArgument(ARG_ARTICLE));
			return true;
		}
		
		if (cmdType == CommandConstants.UNDO_COMMAND) {
			client.Unsubscribe(getHost(), getPort(), getArgument(ARG_ARTICLE));
			return true;
		}
				
		return false;
	}

}
