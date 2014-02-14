package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
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

	private final int ARG_HOST = 1;
	private final int ARG_PORT = 2;
	private final int ARG_ARTICLE = 3;
	
	private int cmdType;
	
	public SubUnSub(String cmd, int doCommand) {
		super(cmd);
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
			client.Subscribe(getArgument(ARG_HOST), 
					Integer.parseInt(getArgument(ARG_PORT)), 
					getArgument(ARG_ARTICLE));
			return true;
		}
		
		if (cmdType == CommandConstants.UNDO_COMMAND) {
			client.Unsubscribe(getArgument(ARG_HOST), 
					Integer.parseInt(getArgument(ARG_PORT)), 
					getArgument(ARG_ARTICLE));
			return true;
		}
				
		return false;
	}

}
